package com.tistory.jaimemin.paymentservice.payment.application.service

import com.tistory.jaimemin.paymentservice.common.Logger
import com.tistory.jaimemin.paymentservice.common.UseCase
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.PaymentEventMessageRelayUseCase
import com.tistory.jaimemin.paymentservice.payment.application.port.out.DispatchEventMessagePort
import com.tistory.jaimemin.paymentservice.payment.application.port.out.LoadPendingPaymentEventMessagePort
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import reactor.core.scheduler.Schedulers
import java.util.concurrent.TimeUnit

@UseCase
@Profile("dev")
class PaymentEventMessageRelayService(
    private val loadPendingPaymentEventMessageOutboxPort: LoadPendingPaymentEventMessagePort,
    private val dispatchEventMessagePort: DispatchEventMessagePort
) : PaymentEventMessageRelayUseCase {

    private val scheduler = Schedulers.newSingle("message-relay")

    @Scheduled(fixedDelay = 180, initialDelay = 180, timeUnit = TimeUnit.SECONDS)
    override fun relay() {
        loadPendingPaymentEventMessageOutboxPort.getPendingPaymentEventMessage()
            .map {
                dispatchEventMessagePort.dispatch(it)
            }
            .onErrorContinue { err, _ -> Logger.error("messageRelay", err.message ?: "failed to relay message", err) }
            .subscribeOn(scheduler)
            .subscribe()
    }
}