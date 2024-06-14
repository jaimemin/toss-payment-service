package com.tistory.jaimemin.paymentservice.payment.adapter.out.persistent.repository

import com.tistory.jaimemin.paymentservice.payment.application.port.out.PaymentStatusUpdateCommand
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentEventMessage
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentEventMessageType
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentOutboxRepository {

    fun insertOutbox(command: PaymentStatusUpdateCommand): Mono<PaymentEventMessage>

    fun markMessageAsSent(idempotencyKey: String, type: PaymentEventMessageType): Mono<Boolean>

    fun markMessageAsFailure(idempotencyKey: String, type: PaymentEventMessageType): Mono<Boolean>

    fun getPendingPaymentOutboxes(): Flux<PaymentEventMessage>
}