package com.tistory.jaimemin.paymentservice.payment.adapter.out.persistent.exception

import com.tistory.jaimemin.paymentservice.payment.domain.PaymentStatus

class PaymentAlreadyProcessedException(
    val status: PaymentStatus,
    message: String
) : RuntimeException(message)