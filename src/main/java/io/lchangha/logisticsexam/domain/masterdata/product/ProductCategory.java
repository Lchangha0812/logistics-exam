package io.lchangha.logisticsexam.domain.masterdata.product;

public enum ProductCategory {
    GENERAL("일반 상품"),
    FOOD("식품"),
    ELECTRONICS("전자제품"),
    HAZARDOUS("위험물");

    private final String description;

    ProductCategory(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}