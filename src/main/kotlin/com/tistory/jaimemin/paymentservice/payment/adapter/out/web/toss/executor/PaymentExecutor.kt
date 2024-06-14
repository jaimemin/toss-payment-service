package com.tistory.jaimemin.paymentservice.payment.adapter.out.web.toss.executor

import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.PaymentConfirmCommand
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentExecutionResult
import reactor.core.publisher.Mono

/**
 * 실제 외부 PSP에 결제 승인 요청을 전달하는 인터페이스
 */
interface PaymentExecutor {

    fun execute(command: PaymentConfirmCommand): Mono<PaymentExecutionResult>
}