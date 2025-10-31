package io.lchangha.logisticsexam.warehouse.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record WarehouseDescription(String value) {
    public WarehouseDescription {
        DomainValidator.requireNonBlank(value, () -> new DomainException("창고 설명은 필수이며 비어 있을 수 없습니다."));
    }

    public static WarehouseDescription of(String value) {
        return new WarehouseDescription(value);
    }
}
