package io.lchangha.logisticsexam.receiving.domain.vo;

/**
 * 입고의 출처를 식별하여 비즈니스 로직을 분기할 수 있도록 하는 열거형입니다.
 */
public enum GoodsReceiptType {
    PO_BASED,
    FREE // 구매 발주 없이 진행되는 입고
}
