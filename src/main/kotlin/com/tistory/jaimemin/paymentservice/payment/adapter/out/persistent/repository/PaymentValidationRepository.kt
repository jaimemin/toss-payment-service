package com.tistory.jaimemin.paymentservice.payment.adapter.out.persistent.repository

import reactor.core.publisher.Mono

interface PaymentValidationRepository {

    fun isValid(orderId: String, amount: Long): Mono<Boolean>
}