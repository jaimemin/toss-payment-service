package com.tistory.jaimemin.paymentservice.payment.adapter.out.web.product.client

import com.tistory.jaimemin.paymentservice.payment.domain.Product
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class MockProductClient : ProductClient {

    override fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product> {
        return Flux.fromIterable(
            productIds.map {
                Product(
                    id = it,
                    amount = it * 10000,
                    quality = 2,
                    name = "test_product_$it",
                    sellerId = 1
                )
            }
        )
    }

}