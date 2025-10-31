package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record ErpOrderNumber(String value) {
    public ErpOrderNumber {
        DomainValidator.requireNonBlank(value, () -> new DomainException("ERP 주문 번호는 필수이며 비어 있을 수 없습니다."));
    }

    public static ErpOrderNumber of(String value) {
        return new ErpOrderNumber(value);
    }
}
