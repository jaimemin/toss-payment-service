package com.tistory.jaimemin.paymentservice.payment.domain

data class PendingPaymentEvent(
    val paymentEventId: Long,
    val paymentKey: String,
    val orderId: String,
    val pendingPaymentOrders: List<PendingPaymentOrder>
) {
    fun totalAmount(): Long {
        return pendingPaymentOrders.sumOf { it.amount }
    }
}