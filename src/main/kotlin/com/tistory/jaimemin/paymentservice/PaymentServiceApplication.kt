package com.tistory.jaimemin.paymentservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PaymentServiceApplication

/**
 * docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=1234 -d -p 3306:3306 mysql:8.2.0
 *
 * docker exec -it mysql-container mysql -u root -p
 *
 * CREATE DATABASE test;
 *
 * https://github.com/tosspayments/payment-widget-sample/blob/main/node-vanillajs
 */
fun main(args: Array<String>) {
    runApplication<PaymentServiceApplication>(*args)
}
