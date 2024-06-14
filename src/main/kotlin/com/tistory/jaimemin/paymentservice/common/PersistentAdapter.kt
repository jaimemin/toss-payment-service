package com.tistory.jaimemin.paymentservice.common

import org.springframework.stereotype.Component

/**
 * 외부와 연결되는 Adapter임을 알리기 위한 어노테이션 정의
 */
@Component
@Target(AnnotationTarget.CLASS)
annotation class PersistentAdapter()
