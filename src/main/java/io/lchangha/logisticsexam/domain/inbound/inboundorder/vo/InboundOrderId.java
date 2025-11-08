package io.lchangha.logisticsexam.domain.inbound.inboundorder.vo;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record InboundOrderId(Long value) {
    public InboundOrderId {
        DomainValidator.requireNonNull(value, () -> new DomainException("입고 주문 ID는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("입고 주문 ID는 0보다 커야 합니다."));
    }

    public static InboundOrderId of(Long value) {
        return new InboundOrderId(value);
    }
}
