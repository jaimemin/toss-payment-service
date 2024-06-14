package com.tistory.jaimemin.paymentservice.payment.adapter.`in`.web.request

import java.time.LocalDateTime

data class CheckoutRequest(
    val cartId: Long = 1,
    val productIds: List<Long> = listOf(1, 2, 3),
    val buyerId: Long = 1,
    val seed: String = LocalDateTime.now().toString() // 동일한 물건을 구입하더라도 요청을 구별할 수 있도록
)