package com.tistory.jaimemin.paymentservice.payment.application.port.out

import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentExecutionResult
import reactor.core.publisher.Mono

interface PaymentExecutorPort {

    fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult>
}