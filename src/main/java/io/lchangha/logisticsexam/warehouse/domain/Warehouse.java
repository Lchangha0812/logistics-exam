package io.lchangha.logisticsexam.warehouse.domain;

import io.lchangha.logisticsexam.common.domain.ContactName;
import io.lchangha.logisticsexam.common.domain.EmailAddress;
import io.lchangha.logisticsexam.common.domain.PhoneNumber;
import io.lchangha.logisticsexam.common.util.DomainValidator;
import io.lchangha.logisticsexam.id.WarehouseId;
import io.lchangha.logisticsexam.location.domain.Location;
import io.lchangha.logisticsexam.warehouse.exception.InvalidWarehouseException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * {@code Warehouse} 애그리거트 루트는 WMS(창고 관리 시스템)에서 관리되는 물리적인 창고 시설을 정의합니다。
 * 각 창고는 독립적인 운영 단위로 간주되며, 상품의 보관 및 이동이 이루어지는 핵심 공간입니다。
 *
 * 이 애그리거트는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**창고 식별:** {@link WarehouseId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**기본 정보 정의:** 창고명({@link WarehouseName}), 설명({@link WarehouseDescription}), 주소({@link WarehouseAddress}) 등 기본적인 식별 정보를 제공합니다.</li>
 *     <li>**연락처 정보:** 담당자 이름({@link ContactName}), 전화번호({@link PhoneNumber}), 이메일({@link EmailAddress}) 등 창고 운영 관련 소통에 필요한 정보를 관리합니다.</li>
 *     <li>**창고 유형 분류:** {@link WarehouseType}을 통해 창고의 특성(예: 일반 창고, 냉동 창고, 자동화 창고)을 구분합니다.</li>
 *     <li>**창고 상태 관리:** {@link WarehouseStatus}를 통해 창고의 현재 운영 상태(예: 운영 중, 폐쇄, 유지보수 중)를 추적합니다.</li>
 * </ul>
 *
 * {@code Warehouse}는 {@link Location} 애그리거트의 상위 개념으로, 여러 로케이션을 포함할 수 있으며,
 * 입고({@link io.lchangha.logisticsexam.inbound.domain.Receiving}) 및 출고와 같은 주요 물류 프로세스의 대상이 됩니다。
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Warehouse {
    private WarehouseId id;
    private final WarehouseName name;
    private final WarehouseDescription description;
    private final WarehouseAddress address;
    private final ContactName contactName;
    private final PhoneNumber contactPhone;
    private final EmailAddress contactEmail;
    private final WarehouseType type;
    private final WarehouseStatus status;

    @Builder(access = AccessLevel.PACKAGE)
    private Warehouse(
            WarehouseId id,
            WarehouseName name,
            WarehouseDescription description,
            WarehouseAddress address,
            ContactName contactName,
            PhoneNumber contactPhone,
            EmailAddress contactEmail,
            WarehouseType type,
            WarehouseStatus status) {
        this.id = id;
        this.name = DomainValidator.requireNonNull(name, () -> new InvalidWarehouseException("창고명은 필수입니다."));
        this.description = DomainValidator.requireNonNull(description, () -> new InvalidWarehouseException("창고 설명은 필수입니다."));
        this.address = DomainValidator.requireNonNull(address, () -> new InvalidWarehouseException("창고 주소는 필수입니다."));
        this.contactName = DomainValidator.requireNonNull(contactName, () -> new InvalidWarehouseException("담당자 이름은 필수입니다."));
        this.contactPhone = DomainValidator.requireNonNull(contactPhone, () -> new InvalidWarehouseException("담당자 전화번호는 필수입니다."));
        this.contactEmail = DomainValidator.requireNonNull(contactEmail, () -> new InvalidWarehouseException("담당자 이메일은 필수입니다."));
        this.type = DomainValidator.requireNonNull(type, () -> new InvalidWarehouseException("창고 유형은 필수입니다."));
        this.status = DomainValidator.requireNonNull(status, () -> new InvalidWarehouseException("창고 상태는 필수입니다."));
    }
}
