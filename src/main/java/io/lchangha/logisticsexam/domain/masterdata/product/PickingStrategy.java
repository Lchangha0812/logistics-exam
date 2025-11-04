package io.lchangha.logisticsexam.domain.masterdata.product;

public enum PickingStrategy {
    BATCH_PICKING("일괄 피킹"),
    ZONE_PICKING("존 피킹"),
    WAVE_PICKING("웨이브 피킹"),
    SINGLE_ORDER_PICKING("단일 주문 피킹");

    private final String description;

    PickingStrategy(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
