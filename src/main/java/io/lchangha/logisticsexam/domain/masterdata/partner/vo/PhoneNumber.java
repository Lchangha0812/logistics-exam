package io.lchangha.logisticsexam.domain.masterdata.partner.vo;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record PhoneNumber(String value) {
    public PhoneNumber {
        DomainValidator.requireNonBlank(value, () -> new DomainException("전화번호는 필수이며 비어 있을 수 없습니다."));
        // 간단한 전화번호 형식 검증 (예: 숫자, 하이픈만 허용)
        DomainValidator.isTrue(value.matches("^[0-9\\-]+\"\"$"), () -> new DomainException("전화번호 형식이 올바르지 않습니다."));
    }

    public static PhoneNumber of(String value) {
        return new PhoneNumber(value);
    }
}
