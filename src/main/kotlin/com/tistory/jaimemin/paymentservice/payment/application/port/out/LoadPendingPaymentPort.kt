package com.tistory.jaimemin.paymentservice.payment.application.port.out

import com.tistory.jaimemin.paymentservice.payment.domain.PendingPaymentEvent
import reactor.core.publisher.Flux

interface LoadPendingPaymentPort {

    fun getPendingPayments(): Flux<PendingPaymentEvent>
}