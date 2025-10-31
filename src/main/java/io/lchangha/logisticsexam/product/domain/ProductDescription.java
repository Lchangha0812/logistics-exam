package io.lchangha.logisticsexam.product.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record ProductDescription(String value) {
    public ProductDescription {
        DomainValidator.requireNonBlank(value, () -> new DomainException("상품 설명은 필수이며 비어 있을 수 없습니다."));
    }

    public static ProductDescription of(String value) {
        return new ProductDescription(value);
    }
}
