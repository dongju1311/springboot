package com.springboot.shoppy_fullstack_app.entity;

/**
 * 고정된 상수 값 집합을 표현하는 특별한 클래스 타입
 */
public enum OrderStatus {
    대기중,
    결제중,
    결제완료,
    취소,
    환불,
    만료
}
