package com.tistory.jaimemin.paymentservice.payment.application.service

import com.tistory.jaimemin.paymentservice.common.UseCase
import com.tistory.jaimemin.paymentservice.payment.adapter.out.persistent.exception.PaymentAlreadyProcessedException
import com.tistory.jaimemin.paymentservice.payment.adapter.out.persistent.exception.PaymentValidationException
import com.tistory.jaimemin.paymentservice.payment.adapter.out.web.toss.exception.PSPConfirmationException
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.PaymentConfirmUseCase
import com.tistory.jaimemin.paymentservice.payment.application.port.out.PaymentExecutorPort
import com.tistory.jaimemin.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import com.tistory.jaimemin.paymentservice.payment.application.port.out.PaymentStatusUpdatePort
import com.tistory.jaimemin.paymentservice.payment.application.port.out.PaymentValidationPort
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentConfirmationResult
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentFailure
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentStatus
import io.netty.handler.timeout.TimeoutException
import reactor.core.publisher.Mono

@UseCase
class PaymentConfirmService(
    private val paymentStatusUpdatePort: PaymentStatusUpdatePort,
    private val paymentValidationPort: PaymentValidationPort,
    private val paymentExecutorPort: PaymentExecutorPort
) : PaymentConfirmUseCase {

    override fun confirm(command: PaymentConfirmCommand): Mono<PaymentConfirmationResult> {
        return paymentStatusUpdatePort.updatePaymentStatusToExecuting(command.orderId, command.paymentKey)
            .filterWhen { paymentValidationPort.isValid(command.orderId, command.amount) }
            .flatMap { paymentExecutorPort.execute(command) }
            .flatMap {
                paymentStatusUpdatePort.updatePaymentStatus(
                    command = PaymentStatusUpdateCommand(
                        paymentKey = it.paymentKey,
                        orderId = it.orderId,
                        status = it.paymentStatus(),
                        extraDetails = it.extraDetails,
                        failure = it.failure
                    )
                ).thenReturn(it)
            }
            .map { PaymentConfirmationResult(status = it.paymentStatus(), failure = it.failure) }
            .onErrorResume { handlePaymentError(it, command) }
    }

    private fun handlePaymentError(
        error: Throwable,
        command: PaymentConfirmCommand
    ): Mono<PaymentConfirmationResult> {
        val (status, failure) = when (error) {
            is PSPConfirmationException -> Pair(
                error.paymentStatus(),
                PaymentFailure(error.errorCode, error.errorMessage)
            )

            is PaymentValidationException -> Pair(
                PaymentStatus.FAILURE,
                PaymentFailure(error::class.simpleName ?: "", error.message ?: "")
            )

            is PaymentAlreadyProcessedException -> return Mono.just(
                PaymentConfirmationResult(
                    status = error.status,
                    failure = PaymentFailure(errorCode = error::class.simpleName ?: "", message = error.message ?: "")
                )
            )

            is TimeoutException -> Pair(
                PaymentStatus.UNKNOWN,
                PaymentFailure(error::class.simpleName ?: "", error.message ?: "")
            )

            else -> Pair(PaymentStatus.UNKNOWN, PaymentFailure(error::class.simpleName ?: "", error.message ?: ""))
        }

        val paymentStatusUpdateCommand = PaymentStatusUpdateCommand(
            paymentKey = command.paymentKey,
            orderId = command.orderId,
            status = status,
            failure = failure
        )

        return paymentStatusUpdatePort.updatePaymentStatus(paymentStatusUpdateCommand)
            .map { PaymentConfirmationResult(status, failure) }
    }
}