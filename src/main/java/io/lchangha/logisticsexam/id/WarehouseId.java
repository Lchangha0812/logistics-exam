package io.lchangha.logisticsexam.id;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record WarehouseId(Long value) {
    public WarehouseId {
        DomainValidator.requireNonNull(value, () -> new DomainException("창고 ID는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("창고 ID는 0보다 커야 합니다."));
    }

    public static WarehouseId of(Long value) {
        return new WarehouseId(value);
    }
}
