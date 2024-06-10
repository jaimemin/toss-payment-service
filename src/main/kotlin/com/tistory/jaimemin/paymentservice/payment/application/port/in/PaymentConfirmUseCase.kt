package com.tistory.jaimemin.paymentservice.payment.application.port.`in`

import com.tistory.jaimemin.paymentservice.payment.domain.PaymentConfirmationResult
import reactor.core.publisher.Mono

interface PaymentConfirmUseCase {

    fun confirm(command: PaymentConfirmCommand): Mono<PaymentConfirmationResult>
}