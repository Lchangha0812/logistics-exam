package io.lchangha.logisticsexam.domain.inbound.arrival;

public enum ClaimType {
    DAMAGE, // 파손
    SHORTAGE, // 수량 부족
    OVERAGE, // 수량 초과
    WRONG_ITEM // 다른 상품
}
