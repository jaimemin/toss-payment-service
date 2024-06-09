package com.tistory.jaimemin.paymentservice.payment.adapter.out.web.product.client

import com.tistory.jaimemin.paymentservice.payment.domain.Product
import reactor.core.publisher.Flux

interface ProductClient {

    fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product>
}