package com.tistory.jaimemin.paymentservice.payment.application.port.out

import com.tistory.jaimemin.paymentservice.payment.domain.PaymentEvent
import reactor.core.publisher.Mono

interface SavePaymentPort {

    fun save(paymentEvent: PaymentEvent): Mono<Void>
}