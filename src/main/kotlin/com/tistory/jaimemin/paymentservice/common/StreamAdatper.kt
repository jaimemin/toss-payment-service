package com.tistory.jaimemin.paymentservice.common

import org.springframework.stereotype.Component

/**
 * 어플리케이션 외부 세상인 메시지 큐와 연결된다는 의미
 */
@Component
@Target(AnnotationTarget.CLASS)
annotation class StreamAdatper()
