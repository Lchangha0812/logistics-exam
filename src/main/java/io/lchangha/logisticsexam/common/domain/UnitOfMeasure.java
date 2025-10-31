package io.lchangha.logisticsexam.common.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record UnitOfMeasure(String value) {
    public UnitOfMeasure {
        DomainValidator.requireNonBlank(value, () -> new DomainException("측정 단위는 필수이며 비어 있을 수 없습니다."));
    }

    public static UnitOfMeasure of(String value) {
        return new UnitOfMeasure(value);
    }
}
