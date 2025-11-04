package io.lchangha.logisticsexam.domain.masterdata.product;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record ProductDescription(String value) {
    public ProductDescription {
        DomainValidator.requireNonBlank(value, () -> new DomainException("상품 설명은 필수이며 비어 있을 수 없습니다."));
    }

    public static ProductDescription of(String value) {
        return new ProductDescription(value);
    }
}
