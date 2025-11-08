package io.lchangha.logisticsexam.domain.masterdata.partner.vo;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record PartnerName(String value) {
    public PartnerName {
        DomainValidator.requireNonBlank(value, () -> new DomainException("파트너 이름은 필수이며 비어 있을 수 없습니다."));
    }

    public static PartnerName of(String value) {
        return new PartnerName(value);
    }
}
