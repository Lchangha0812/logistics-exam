package io.lchangha.logisticsexam.domain.inbound.arrival;

public enum ArrivalStatus {
    COMPLETED("입하 완료"); // 입하 이벤트가 기록되고 완료되었음을 의미

    private final String description;

    ArrivalStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
