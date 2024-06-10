package com.tistory.jaimemin.paymentservice.payment.adapter.`in`.web.api

import com.tistory.jaimemin.paymentservice.common.WebAdapter
import com.tistory.jaimemin.paymentservice.payment.adapter.`in`.web.request.TossPaymentConfirmRequest
import com.tistory.jaimemin.paymentservice.payment.adapter.`in`.web.response.ApiResponse
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.PaymentConfirmUseCase
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentConfirmationResult
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@WebAdapter
@RestController
@RequestMapping("/v1/toss")
class TossPaymentController(
    private val paymentConfirmUseCase: PaymentConfirmUseCase
) {

    @PostMapping("/confirm")
    fun confirm(@RequestBody request: TossPaymentConfirmRequest): Mono<ResponseEntity<ApiResponse<PaymentConfirmationResult>>> {
        val command = PaymentConfirmCommand(
            paymentKey = request.paymentKey,
            orderId = request.orderId,
            amount = request.amount
        )

        return paymentConfirmUseCase.confirm(command)
            .map { ResponseEntity.ok().body(ApiResponse.with(HttpStatus.OK, "", it)) }
    }
}