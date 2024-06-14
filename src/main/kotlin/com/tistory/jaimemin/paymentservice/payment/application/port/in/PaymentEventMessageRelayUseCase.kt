package com.tistory.jaimemin.paymentservice.payment.application.port.`in`

/**
 * 아직 kafka로 전송되지 않은 토픽들을 불러와 kafka로 보냄
 */
interface PaymentEventMessageRelayUseCase {

    fun relay()
}