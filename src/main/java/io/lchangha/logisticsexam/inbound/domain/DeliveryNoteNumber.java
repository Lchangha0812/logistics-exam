package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record DeliveryNoteNumber(String value) {
    public DeliveryNoteNumber {
        DomainValidator.requireNonBlank(value, () -> new DomainException("납품서 번호는 필수이며 비어 있을 수 없습니다."));
    }

    public static DeliveryNoteNumber of(String value) {
        return new DeliveryNoteNumber(value);
    }
}
