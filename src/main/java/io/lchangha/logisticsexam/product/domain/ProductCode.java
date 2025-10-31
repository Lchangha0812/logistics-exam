package io.lchangha.logisticsexam.product.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record ProductCode(String value) {
    public ProductCode {
        DomainValidator.requireNonBlank(value, () -> new DomainException("상품 코드는 필수이며 비어 있을 수 없습니다."));
    }

    public static ProductCode of(String value) {
        return new ProductCode(value);
    }
}