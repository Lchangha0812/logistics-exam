package io.lchangha.logisticsexam.domain.inbound.arrival;

import lombok.Getter;

@Getter
public enum ArrivalStatus {
    COMPLETED("입하 완료"); // 입하 이벤트가 기록되고 완료되었음을 의미

    private final String description;

    ArrivalStatus(String description) {
        this.description = description;
    }

}
