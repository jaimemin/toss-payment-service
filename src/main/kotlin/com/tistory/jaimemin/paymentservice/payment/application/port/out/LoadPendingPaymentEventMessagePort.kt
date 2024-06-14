package com.tistory.jaimemin.paymentservice.payment.application.port.out

import com.tistory.jaimemin.paymentservice.payment.domain.PaymentEventMessage
import reactor.core.publisher.Flux

interface LoadPendingPaymentEventMessagePort {

    fun getPendingPaymentEventMessage(): Flux<PaymentEventMessage>
}