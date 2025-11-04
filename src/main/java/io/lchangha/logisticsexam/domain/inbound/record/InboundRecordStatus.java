package io.lchangha.logisticsexam.domain.inbound.record;

public enum InboundRecordStatus {
    NOT_STARTED("진행 전"),
    IN_PROGRESS("진행 중"),
    COMPLETED("완료"),
    PARTIALLY_COMPLETED("부분 완료"), // 주문량보다 적게 들어왔지만 분납인 경우
    OVER_COMPLETED("과입고 완료"); // 주문량보다 많이 들어온 경우

    private final String description;

    InboundRecordStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
