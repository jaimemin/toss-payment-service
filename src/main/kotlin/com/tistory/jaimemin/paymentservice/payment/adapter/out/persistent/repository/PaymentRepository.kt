package com.tistory.jaimemin.paymentservice.payment.adapter.out.persistent.repository

import com.tistory.jaimemin.paymentservice.payment.domain.PaymentEvent
import com.tistory.jaimemin.paymentservice.payment.domain.PendingPaymentEvent
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentRepository {

    fun save(paymentEvent: PaymentEvent): Mono<Void>

    fun getPendingPayments(): Flux<PendingPaymentEvent>
}