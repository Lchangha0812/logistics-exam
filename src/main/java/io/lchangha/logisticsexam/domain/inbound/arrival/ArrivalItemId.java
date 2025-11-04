package io.lchangha.logisticsexam.domain.inbound.arrival;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record ArrivalItemId(Long value) {
    public ArrivalItemId {
        DomainValidator.requireNonNull(value, () -> new DomainException("입하 상품 ID는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("입하 상품 ID는 0보다 커야 합니다."));
    }

    public static ArrivalItemId of(Long value) {
        return new ArrivalItemId(value);
    }
}
