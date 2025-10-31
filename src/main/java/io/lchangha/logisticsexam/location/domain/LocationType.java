package io.lchangha.logisticsexam.location.domain;

public enum LocationType {
    RACK("랙"),
    FLOOR("평치"),
    BIN("빈"),
    PALLET("팔레트");

    private final String description;

    LocationType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
