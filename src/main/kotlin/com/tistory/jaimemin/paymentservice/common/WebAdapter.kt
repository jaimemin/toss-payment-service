package com.tistory.jaimemin.paymentservice.common

import org.springframework.stereotype.Component

/**
 * Controller는 외부의 웹 요청을 받아서 어플리케이션으로 요청을 전달하는 역할인 WebAdapter
 * 이를 명확하게 나타내기 위해 어노테이션 정의
 */
@Component
@Target(AnnotationTarget.CLASS)
annotation class WebAdapter()
