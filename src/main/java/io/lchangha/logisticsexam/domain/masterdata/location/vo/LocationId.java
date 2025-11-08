package io.lchangha.logisticsexam.domain.masterdata.location.vo;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record LocationId(Long value) {
    public LocationId {
        DomainValidator.requireNonNull(value, () -> new DomainException("로케이션 ID는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("로케이션 ID는 0보다 커야 합니다."));
    }

    public static LocationId of(Long value) {
        return new LocationId(value);
    }
}
