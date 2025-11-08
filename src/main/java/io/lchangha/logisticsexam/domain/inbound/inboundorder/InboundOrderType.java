package io.lchangha.logisticsexam.domain.inbound.inboundorder;

public enum InboundOrderType {
    STANDARD_PURCHASE("일반 구매 입고"),
    WAREHOUSE_TRANSFER("창고 간 이동 입고");

    private final String description;

    InboundOrderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
