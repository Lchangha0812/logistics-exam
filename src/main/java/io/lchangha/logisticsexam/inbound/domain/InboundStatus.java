package io.lchangha.logisticsexam.inbound.domain;

public enum InboundStatus {
    REQUESTED("입고 요청"),
    CONFIRMED("입고 확정"),
    REJECTED("입고 거절");

    private final String description;

    InboundStatus(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}