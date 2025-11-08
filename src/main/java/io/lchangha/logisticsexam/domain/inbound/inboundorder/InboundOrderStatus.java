package io.lchangha.logisticsexam.domain.inbound.inboundorder;

public enum InboundOrderStatus {
    REQUESTED("요청됨"),
    CONFIRMED("확정됨"),
    CANCELLED("취소됨"),
    COMPLETED("완료"), // 모든 수량이 입고됨
    INCOMPLETE("불완료"); // 주문이 마감되었으나, 일부 수량이 입고되지 않음

    private final String description;

    InboundOrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
