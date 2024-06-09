package com.tistory.jaimemin.paymentservice.payment.adapter.out.persistent

import com.tistory.jaimemin.paymentservice.common.PersistentAdapter
import com.tistory.jaimemin.paymentservice.payment.adapter.out.persistent.repository.PaymentRepository
import com.tistory.jaimemin.paymentservice.payment.application.port.out.SavePaymentPort
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentEvent
import reactor.core.publisher.Mono

@PersistentAdapter
class PaymentPersistentAdapter(
    private val paymentRepository: PaymentRepository
) : SavePaymentPort {

    override fun save(paymentEvent: PaymentEvent): Mono<Void> {
        return paymentRepository.save(paymentEvent)
    }
}