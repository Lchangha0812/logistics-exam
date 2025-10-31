package io.lchangha.logisticsexam.inbound.domain;

public enum ArrivalStatus {
    SCHEDULED("입하 예정"),
    ARRIVED("입하 완료"),
    INSPECTING("검품 중"),
    COMPLETED("입하 처리 완료"),
    REJECTED("입하 거절"); // 입하 단계에서 거절될 수 있음

    private final String description;

    ArrivalStatus(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
