package io.lchangha.logisticsexam.inbound.domain;

public enum InboundOrderStatus {
    REQUESTED("입고 요청"),
    CONFIRMED("입고 확정"),
    CANCELLED("입고 취소"); // Renamed from REJECTED to CANCELLED for order status

    private final String description;

    InboundOrderStatus(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
