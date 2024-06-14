package com.tistory.jaimemin.paymentservice.payment.adapter.out.stream.util

import org.springframework.stereotype.Component
import kotlin.math.abs

@Component
class PartitionKeyUtil {

    val PARTITION_KEY_COUNT = 6 // kafka payment topic parition 값이 6

    fun createPartitionKey(number: Int): Int {
        return abs(number) % PARTITION_KEY_COUNT
    }
}