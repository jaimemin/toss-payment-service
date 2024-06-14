package com.tistory.jaimemin.paymentservice.payment.adapter.`in`.web.view

import com.tistory.jaimemin.paymentservice.IdempotencyCreator
import com.tistory.jaimemin.paymentservice.common.WebAdapter
import com.tistory.jaimemin.paymentservice.payment.adapter.`in`.web.request.CheckoutRequest
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.CheckoutCommand
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.CheckoutUseCase
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@Controller
@WebAdapter
class CheckoutController(
    private val checkoutUseCase: CheckoutUseCase
) {

    @GetMapping("/")
    fun checkoutPage(request: CheckoutRequest, model: Model): Mono<String> {
        val command = CheckoutCommand(
            cartId = request.cartId,
            buyerId = request.buyerId,
            productIds = request.productIds,
            idempotencyKey = IdempotencyCreator.create(request.seed) // 원래는 request 그 자체가 전달되어야 함
        )

        return checkoutUseCase.checkout(command)
            .map {
                model.addAttribute("orderId", it.orderId)
                model.addAttribute("orderName", it.orderName)
                model.addAttribute("amount", it.amount)
                "checkout"
            }
    }
}