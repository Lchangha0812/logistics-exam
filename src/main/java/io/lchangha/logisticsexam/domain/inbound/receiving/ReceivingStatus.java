package io.lchangha.logisticsexam.domain.inbound.receiving;

public enum ReceivingStatus {

    PUTAWAY_COMPLETED("적치 완료"),
    REJECTED("입고 거절"); // 입고(적치) 단계에서 거절될 수 있음

    private final String description;

    ReceivingStatus(final String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
