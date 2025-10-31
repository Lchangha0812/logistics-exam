package io.lchangha.logisticsexam.inbound.domain;

public enum ReceivingStatus {
    PENDING_INSPECTION("검품 대기"),
    INSPECTED("검품 완료"),
    PENDING_PUTAWAY("적치 대기"),
    PUTAWAY_COMPLETED("적치 완료"),
    REJECTED("입고 거절"); // 입고 단계에서 거절될 수 있음

    private final String description;

    ReceivingStatus(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
