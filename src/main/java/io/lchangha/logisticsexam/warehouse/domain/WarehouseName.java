package io.lchangha.logisticsexam.warehouse.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record WarehouseName(String value) {
    public WarehouseName {
        DomainValidator.requireNonBlank(value, () -> new DomainException("창고명은 필수이며 비어 있을 수 없습니다."));
    }

    public static WarehouseName of(String value) {
        return new WarehouseName(value);
    }
}
