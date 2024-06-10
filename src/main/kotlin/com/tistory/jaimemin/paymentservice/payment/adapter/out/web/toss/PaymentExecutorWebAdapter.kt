package com.tistory.jaimemin.paymentservice.payment.adapter.out.web.toss

import com.tistory.jaimemin.paymentservice.common.WebAdapter
import com.tistory.jaimemin.paymentservice.payment.adapter.out.web.toss.executor.PaymentExecutor
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import com.tistory.jaimemin.paymentservice.payment.application.port.out.PaymentExecutorPort
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentExecutionResult
import reactor.core.publisher.Mono

@WebAdapter
class PaymentExecutorWebAdapter(
    private val paymentExecutor: PaymentExecutor
) : PaymentExecutorPort {

    override fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult> {
        return paymentExecutor.execute(command)
    }
}