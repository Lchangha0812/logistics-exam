package io.lchangha.logisticsexam.warehouse.domain;

public enum WarehouseType {
    GENERAL("일반 창고"),
    COLD_STORAGE("냉장/냉동 창고"),
    BONDED("보세 창고"),
    HAZARDOUS("위험물 창고");

    private final String description;

    WarehouseType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
