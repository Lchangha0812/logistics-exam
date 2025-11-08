package io.lchangha.logisticsexam.domain.masterdata.partner.vo;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record PartnerAddress(
        String address1,
        String address2,
        String city,
        String state,
        String zipCode,
        String country
) {
    public PartnerAddress {
        DomainValidator.requireNonBlank(address1, () -> new DomainException("주소1은 필수이며 비어 있을 수 없습니다."));
        DomainValidator.requireNonBlank(city, () -> new DomainException("도시는 필수이며 비어 있을 수 없습니다."));
        DomainValidator.requireNonBlank(state, () -> new DomainException("주는 필수이며 비어 있을 수 없습니다."));
        DomainValidator.requireNonBlank(zipCode, () -> new DomainException("우편번호는 필수이며 비어 있을 수 없습니다."));
        DomainValidator.requireNonBlank(country, () -> new DomainException("국가는 필수이며 비어 있을 수 없습니다."));
    }

    public static PartnerAddress of(String address1, String address2, String city, String state, String zipCode, String country) {
        return new PartnerAddress(address1, address2, city, state, zipCode, country);
    }
}
