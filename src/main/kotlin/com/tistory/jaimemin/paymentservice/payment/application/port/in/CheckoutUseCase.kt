package com.tistory.jaimemin.paymentservice.payment.application.port.`in`

import com.tistory.jaimemin.paymentservice.payment.domain.CheckoutResult
import reactor.core.publisher.Mono

interface CheckoutUseCase {

    fun checkout(command: CheckoutCommand): Mono<CheckoutResult>
}