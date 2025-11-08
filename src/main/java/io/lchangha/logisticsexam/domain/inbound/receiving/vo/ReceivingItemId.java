package io.lchangha.logisticsexam.domain.inbound.receiving.vo;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record ReceivingItemId(Long value) {
    public ReceivingItemId {
        DomainValidator.requireNonNull(value, () -> new DomainException("입고 상품 ID는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("입고 상품 ID는 0보다 커야 합니다."));
    }

    public static ReceivingItemId of(Long value) {
        return new ReceivingItemId(value);
    }
}
