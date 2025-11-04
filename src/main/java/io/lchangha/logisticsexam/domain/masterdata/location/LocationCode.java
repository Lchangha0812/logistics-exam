package io.lchangha.logisticsexam.domain.masterdata.location;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record LocationCode(String value) {
    public LocationCode {
        DomainValidator.requireNonBlank(value, () -> new DomainException("로케이션 코드는 필수이며 비어 있을 수 없습니다."));
    }

    public static LocationCode of(String value) {
        return new LocationCode(value);
    }
}
