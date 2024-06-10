package com.tistory.jaimemin.paymentservice.payment.domain

import java.time.LocalDateTime

data class PaymentExecutionResult(
    val paymentKey: String,
    val orderId: String,
    val extraDetails: PaymentExtraDetails? = null,
    val failure: PaymentExecutionFailure? = null,
    val isSuccess: Boolean,
    val isFailure: Boolean,
    val isUnknown: Boolean,
    val isRetryable: Boolean
) {
    fun paymentStatus(): PaymentStatus {
        return when {
            isSuccess -> PaymentStatus.SUCCESS
            isFailure -> PaymentStatus.FAILURE
            isUnknown -> PaymentStatus.UNKNOWN
            else -> error("결제 (orderId: $orderId) 는 올바르지 않은 결제 상태입니다.")
        }
    }

    init {
        require(listOf(isSuccess, isFailure, isUnknown).count { it } == 1) {
            "결제 (orderId: $orderId)는 올바르지 않은 경제 상태입니다."
        }
    }
}

data class PaymentExtraDetails(
    val type: PaymentType,
    val method: PaymentMethod,
    val approvedAt: LocalDateTime,
    val orderName: String,
    val pspConfirmationStatus: PSPConfirmationStatus,
    val totalAmount: Long,
    val pspRawData: String
)

data class PaymentExecutionFailure(
    val errorCode: String,
    val message: String
)