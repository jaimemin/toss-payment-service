package com.tistory.jaimemin.paymentservice.payment.adapter.out.web.toss.executor

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tistory.jaimemin.paymentservice.payment.adapter.out.web.toss.ressponse.TossPaymentConfirmationResponse
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import com.tistory.jaimemin.paymentservice.payment.domain.*
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class TossPaymentExecutor(
    private val tossPaymentWebClient: WebClient,
    private val uri: String = "/v1/payments/confirm"
) : PaymentExecutor {

    override fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult> {
        return tossPaymentWebClient.post()
            .uri(uri)
            .header("Idempotency-Key", command.orderId)
            .bodyValue(
                """
                {
                    "paymentKey": "${command.paymentKey}",
                    "orderId": "${command.orderId}",
                    "amount": ${command.amount}
                }
            """.trimIndent()
            )
            .retrieve()
            .bodyToMono(TossPaymentConfirmationResponse::class.java)
            .map {
                val objectMapper = jacksonObjectMapper()
                val jsonString = objectMapper.writeValueAsString(it)

                PaymentExecutionResult(
                    paymentKey = command.paymentKey,
                    orderId = command.orderId,
                    extraDetails = PaymentExtraDetails(
                        type = PaymentType.get(it.type),
                        method = PaymentMethod.get(it.method),
                        approvedAt = LocalDateTime.parse(it.approvedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME),
                        pspRawData = jsonString,
                        orderName = it.orderName,
                        pspConfirmationStatus = PSPConfirmationStatus.get(it.status),
                        totalAmount = it.totalAmount.toLong()
                    ),
                    isSuccess = true,
                    isFailure = false,
                    isUnknown = false,
                    isRetryable = false
                )
            }
    }
}