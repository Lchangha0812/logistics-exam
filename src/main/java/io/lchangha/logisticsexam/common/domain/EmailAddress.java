package io.lchangha.logisticsexam.common.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record EmailAddress(String value) {
    public EmailAddress {
        DomainValidator.requireNonBlank(value, () -> new DomainException("이메일 주소는 필수이며 비어 있을 수 없습니다."));
        // 간단한 이메일 형식 검증
        DomainValidator.isTrue(value.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"), () -> new DomainException("이메일 주소 형식이 올바르지 않습니다."));
    }

    public static EmailAddress of(String value) {
        return new EmailAddress(value);
    }
}
