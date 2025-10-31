package io.lchangha.logisticsexam.id;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record PartnerId(Long value) {
    public PartnerId {
        DomainValidator.requireNonNull(value, () -> new DomainException("파트너 ID는 필수입니다."));
        DomainValidator.isTrue(value > 0, () -> new DomainException("파트너 ID는 0보다 커야 합니다."));
    }

    public static PartnerId of(Long value) {
        return new PartnerId(value);
    }
}