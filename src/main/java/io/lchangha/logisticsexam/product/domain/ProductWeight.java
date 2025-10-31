package io.lchangha.logisticsexam.product.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record ProductWeight(Long value) {
    public ProductWeight {
        DomainValidator.requireNonNull(value, () -> new DomainException("상품 무게는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("상품 무게는 0보다 커야 합니다."));
    }

    public static ProductWeight of(Long value) {
        return new ProductWeight(value);
    }
}
