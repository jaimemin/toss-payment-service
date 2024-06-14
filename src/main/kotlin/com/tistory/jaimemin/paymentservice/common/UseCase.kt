package com.tistory.jaimemin.paymentservice.common

import org.springframework.stereotype.Component

/**
 * 어플리케이션이 제공하는 핵심 기능들의 작업 흐름을 나타내는 어노테이션
 * UseCase에서 도메인 패키지들의 클래스들이 상호작용하면서 비즈니스 로직 처리
 */
@Component
@Target(AnnotationTarget.CLASS)
annotation class UseCase()
