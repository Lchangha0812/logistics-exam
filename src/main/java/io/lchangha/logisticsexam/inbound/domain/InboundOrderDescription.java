package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record InboundOrderDescription(String value) {
    public InboundOrderDescription {
        DomainValidator.requireNonBlank(value, () -> new DomainException("입고 주문 설명은 필수이며 비어 있을 수 없습니다."));
    }

    public static InboundOrderDescription of(String value) {
        return new InboundOrderDescription(value);
    }
}
