package com.tistory.jaimemin.paymentservice.payment.application.port.out

import com.tistory.jaimemin.paymentservice.payment.domain.Product
import reactor.core.publisher.Flux

interface LoadProductPort {

    fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product>
}