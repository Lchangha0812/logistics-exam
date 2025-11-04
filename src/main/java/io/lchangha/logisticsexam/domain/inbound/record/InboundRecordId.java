package io.lchangha.logisticsexam.domain.inbound.record;

import java.io.Serializable;

public record InboundRecordId(Long value) implements Serializable {
    public InboundRecordId {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("InboundRecordId는 null이거나 0 이하일 수 없습니다.");
        }
    }
}