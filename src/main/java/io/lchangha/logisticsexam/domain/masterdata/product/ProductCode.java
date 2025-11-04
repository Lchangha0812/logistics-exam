package io.lchangha.logisticsexam.domain.masterdata.product;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record ProductCode(String value) {
    public ProductCode {
        DomainValidator.requireNonBlank(value, () -> new DomainException("상품 코드는 필수이며 비어 있을 수 없습니다."));
    }

    public static ProductCode of(String value) {
        return new ProductCode(value);
    }
}