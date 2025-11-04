package io.lchangha.logisticsexam.domain.masterdata.product;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record ProductName(String value) {
    public ProductName {
        DomainValidator.requireNonBlank(value, () -> new DomainException("상품명은 필수이며 비어 있을 수 없습니다."));
    }

    public static ProductName of(String value) {
        return new ProductName(value);
    }
}
