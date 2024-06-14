package com.tistory.jaimemin.paymentservice.payment.application.port.out

import com.tistory.jaimemin.paymentservice.payment.domain.PaymentEventMessage

interface DispatchEventMessagePort {

    fun dispatch(paymentEventMessage: PaymentEventMessage)
}