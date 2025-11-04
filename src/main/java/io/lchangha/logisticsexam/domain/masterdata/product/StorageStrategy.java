package io.lchangha.logisticsexam.domain.masterdata.product;

public enum StorageStrategy {
    FIFO("선입선출"),
    LIFO("후입선출"),
    FIXED_LOCATION("고정 적치"),
    FREE_LOCATION("자유 적치");

    private final String description;

    StorageStrategy(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
