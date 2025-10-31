package io.lchangha.logisticsexam.partner.domain;

public enum PartnerType {
    SUPPLIER("공급업체"),
    MANUFACTURER("제조업체"),
    CUSTOMER("고객"),
    CARRIER("운송사");

    private final String description;

    PartnerType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
