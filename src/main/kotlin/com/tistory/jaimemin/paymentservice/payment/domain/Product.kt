package com.tistory.jaimemin.paymentservice.payment.domain

data class Product(
    val id: Long,
    val amount: Long,
    val quality: Int,
    val name: String,
    val sellerId: Long
)
