package com.tistory.jaimemin.paymentservice.payment.application.port.`in`

data class PaymentConfirmCommand(
    val paymentKey: String,
    val orderId: String,
    val amount: Long
)
