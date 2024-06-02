package com.tistory.jaimemin.paymentservice.payment.adapter.`in`.web.view

import com.tistory.jaimemin.paymentservice.common.WebAdapter
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import reactor.core.publisher.Mono

@Controller
@WebAdapter
class CheckoutController {

    @GetMapping("/")
    fun checkoutPage(): Mono<String> {
        return Mono.just("checkout")
    }
}