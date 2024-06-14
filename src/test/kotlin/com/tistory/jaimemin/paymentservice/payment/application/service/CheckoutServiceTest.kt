package com.tistory.jaimemin.paymentservice.payment.application.service

import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.CheckoutCommand
import com.tistory.jaimemin.paymentservice.payment.application.port.`in`.CheckoutUseCase
import com.tistory.jaimemin.paymentservice.payment.domain.PaymentEvent
import com.tistory.jaimemin.paymentservice.payment.test.PaymentDatabaseHelper
import com.tistory.jaimemin.paymentservice.payment.test.PaymentTestConfiguration
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.dao.DataIntegrityViolationException
import reactor.test.StepVerifier
import java.util.*

@SpringBootTest
@Import(PaymentTestConfiguration::class)
class CheckoutServiceTest(
    @Autowired private val checkoutUseCase: CheckoutUseCase,
    @Autowired private val paymentDatabaseHelper: PaymentDatabaseHelper
) {
    @BeforeEach
    fun setup() {
        paymentDatabaseHelper.clean()
    }

    @Test
    fun `should save PaymentEvent and PaymentOrder successfully`() {
        val orderId: String = UUID.randomUUID().toString()
        val checkoutCommand = CheckoutCommand(
            cartId = 1,
            buyerId = 1,
            productIds = listOf(1, 2, 3),
            idempotencyKey = orderId
        )

        StepVerifier.create(checkoutUseCase.checkout(checkoutCommand))
            .expectNextMatches {
                it.amount.toInt() == 60000 && it.orderId == orderId
            }
            .verifyComplete()

        val paymentEvent: PaymentEvent = paymentDatabaseHelper.getPayments(orderId)!!

        assertThat(paymentEvent.orderId).isEqualTo(orderId)
        assertThat(paymentEvent.totalAmount()).isEqualTo(60000)
        assertThat(paymentEvent.paymentOrders.size).isEqualTo(checkoutCommand.productIds.size)
        assertFalse(paymentEvent.isPaymentDone())
        assertThat(paymentEvent.paymentOrders.all { !it.isLedgerUpdated() })
        assertThat(paymentEvent.paymentOrders.all { !it.isWalletUpdated() })
    }

    @Test
    fun `should fail to save Payment and PaymentOrder when trying to save for the second time`() {
        val orderId: String = UUID.randomUUID().toString()
        val checkoutCommand = CheckoutCommand(
            cartId = 1,
            buyerId = 1,
            productIds = listOf(1, 2, 3),
            idempotencyKey = orderId
        )

        checkoutUseCase.checkout(checkoutCommand).block()

        assertThrows<DataIntegrityViolationException> {
            checkoutUseCase.checkout(checkoutCommand).block()
        }
    }
}