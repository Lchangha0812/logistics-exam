package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record InboundOrderTitle(String value) {
    public InboundOrderTitle {
        DomainValidator.requireNonBlank(value, () -> new DomainException("입고 주문 제목은 필수이며 비어 있을 수 없습니다."));
    }

    public static InboundOrderTitle of(String value) {
        return new InboundOrderTitle(value);
    }
}
