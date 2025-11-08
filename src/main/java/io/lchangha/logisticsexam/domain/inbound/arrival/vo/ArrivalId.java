package io.lchangha.logisticsexam.domain.inbound.arrival.vo;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record ArrivalId(Long value) {
    public ArrivalId {
        DomainValidator.requireNonNull(value, () -> new DomainException("입하 ID는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("입하 ID는 0보다 커야 합니다."));
    }

    public static ArrivalId of(Long value) {
        return new ArrivalId(value);
    }
}
