package io.lchangha.logisticsexam.product.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record ProductName(String value) {
    public ProductName {
        DomainValidator.requireNonBlank(value, () -> new DomainException("상품명은 필수이며 비어 있을 수 없습니다."));
    }

    public static ProductName of(String value) {
        return new ProductName(value);
    }
}
