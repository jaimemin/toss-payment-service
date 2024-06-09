package com.tistory.jaimemin.paymentservice.payment.adapter.out.web.product

import com.tistory.jaimemin.paymentservice.common.WebAdapter
import com.tistory.jaimemin.paymentservice.payment.adapter.out.web.product.client.ProductClient
import com.tistory.jaimemin.paymentservice.payment.application.port.out.LoadProductPort
import com.tistory.jaimemin.paymentservice.payment.domain.Product
import reactor.core.publisher.Flux

@WebAdapter
class ProductWebAdapter(
    private val productClient: ProductClient
) : LoadProductPort {

    override fun getProducts(cartId: Long, productIds: List<Long>): Flux<Product> {
        return productClient.getProducts(cartId, productIds)
    }
}