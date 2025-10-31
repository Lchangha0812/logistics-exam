package io.lchangha.logisticsexam.partner.domain;

import io.lchangha.logisticsexam.common.domain.ContactName;
import io.lchangha.logisticsexam.common.domain.EmailAddress;
import io.lchangha.logisticsexam.common.domain.PhoneNumber;
import io.lchangha.logisticsexam.common.util.DomainValidator;
import io.lchangha.logisticsexam.id.PartnerId;
import io.lchangha.logisticsexam.partner.exception.InvalidPartnerException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * 파트너 애그리거트 루트 (공급업체, 제조업체, 고객, 운송사 등)
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Partner {
    private PartnerId id;
    private final PartnerName name;
    private final PartnerAddress address;
    private final ContactName contactName;
    private final PhoneNumber contactPhone;
    private final EmailAddress contactEmail;
    private final PartnerType type;

    @Builder(access = AccessLevel.PACKAGE)
    private Partner(
            PartnerId id,
            PartnerName name,
            PartnerAddress address,
            ContactName contactName,
            PhoneNumber contactPhone,
            EmailAddress contactEmail,
            PartnerType type) {
        this.id = id;
        this.name = DomainValidator.requireNonNull(name, () -> new InvalidPartnerException("파트너 이름은 필수입니다."));
        this.address = DomainValidator.requireNonNull(address, () -> new InvalidPartnerException("파트너 주소는 필수입니다."));
        this.contactName = DomainValidator.requireNonNull(contactName, () -> new InvalidPartnerException("담당자 이름은 필수입니다."));
        this.contactPhone = DomainValidator.requireNonNull(contactPhone, () -> new InvalidPartnerException("담당자 전화번호는 필수입니다."));
        this.contactEmail = DomainValidator.requireNonNull(contactEmail, () -> new InvalidPartnerException("담당자 이메일은 필수입니다."));
        this.type = DomainValidator.requireNonNull(type, () -> new InvalidPartnerException("파트너 유형은 필수입니다."));
    }
}