package io.lchangha.logisticsexam.domain.masterdata.partner;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record ContactName(String value) {
    public ContactName {
        DomainValidator.requireNonBlank(value, () -> new DomainException("담당자 이름은 필수이며 비어 있을 수 없습니다."));
    }

    public static ContactName of(String value) {
        return new ContactName(value);
    }
}
