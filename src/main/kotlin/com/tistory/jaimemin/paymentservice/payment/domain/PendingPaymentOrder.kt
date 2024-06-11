package com.tistory.jaimemin.paymentservice.payment.domain

class PendingPaymentOrder(
    val paymentOrderId: Long,
    val status: PaymentStatus,
    val amount: Long,
    val failedCount: Byte,
    val threshold: Byte
)