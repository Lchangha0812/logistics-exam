package io.lchangha.logisticsexam.domain.inbound.inboundorder.vo;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record InboundOrderItemId(Long value) {
    public InboundOrderItemId {
        DomainValidator.requireNonNull(value, () -> new DomainException("입고 주문 상품 ID는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("입고 주문 상품 ID는 0보다 커야 합니다."));
    }

    public static InboundOrderItemId of(Long value) {
        return new InboundOrderItemId(value);
    }
}
