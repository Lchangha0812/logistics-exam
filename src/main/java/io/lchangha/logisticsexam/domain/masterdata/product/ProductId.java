package io.lchangha.logisticsexam.domain.masterdata.product;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record ProductId(Long value) {
    public ProductId {
        DomainValidator.requireNonNull(value, () -> new DomainException("상품 ID는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("상품 ID는 0보다 커야 합니다."));
    }

    public static ProductId of(Long value) {
        return new ProductId(value);
    }
}