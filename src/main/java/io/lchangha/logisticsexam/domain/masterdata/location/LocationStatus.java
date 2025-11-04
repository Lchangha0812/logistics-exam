package io.lchangha.logisticsexam.domain.masterdata.location;

public enum LocationStatus {
    AVAILABLE("사용 가능"),
    OCCUPIED("사용 중"),
    LOCKED("잠금"),
    DAMAGED("손상");

    private final String description;

    LocationStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
