package com.tistory.jaimemin.paymentservice.payment.test

import com.tistory.jaimemin.paymentservice.payment.domain.PaymentEvent
import reactor.core.publisher.Mono

interface PaymentDatabaseHelper {

    fun getPayments(orderId: String): PaymentEvent?

    fun clean(): Mono<Void>
}