package io.lchangha.logisticsexam.receiving.domain.vo;

/**
 * 비즈니스 용어에 기반한 과입고/과소입고 등의 차이 유형을 정의하는 열거형입니다.
 */
public enum DiscrepancyCode {
    OVER, // 과입
    UNDER, // 과소
    EXTRA, // 발주에 없는 품목
    SUBSTITUTE // 대체 품목
}
