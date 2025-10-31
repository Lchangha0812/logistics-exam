package io.lchangha.logisticsexam.warehouse.domain;

public enum WarehouseStatus {
    OPERATING("운영 중"),
    CLOSED("폐쇄"),
    MAINTENANCE("유지보수 중");

    private final String description;

    WarehouseStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
