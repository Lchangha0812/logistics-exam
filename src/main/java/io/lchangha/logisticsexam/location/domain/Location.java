package io.lchangha.logisticsexam.location.domain;

import io.lchangha.logisticsexam.common.util.DomainValidator;
import io.lchangha.logisticsexam.id.LocationId;
import io.lchangha.logisticsexam.id.WarehouseId;
import io.lchangha.logisticsexam.location.exception.InvalidLocationException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * {@code Location} 애그리거트 루트는 WMS(창고 관리 시스템)에서 상품이 실제로 보관되는 물리적인 위치를 정의합니다.
 * 각 로케이션은 특정 창고 내의 고유한 저장 공간을 나타내며, 상품의 정확한 위치 관리를 가능하게 합니다.
 *
 * 이 애그리거트는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**로케이션 식별:** {@link LocationId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**창고 참조:** {@link WarehouseId}를 통해 어떤 창고에 속하는 로케이션인지 연결됩니다.</li>
 *     <li>**로케이션 코드:** {@link LocationCode}를 통해 사람이 읽기 쉬운 형태로 로케이션을 식별합니다 (예: A01-01-01).</li>
 *     <li>**로케이션 유형:** {@link LocationType}을 통해 로케이션의 특성(예: 팔레트 랙, 선반, 벌크)을 구분합니다.</li>
 *     <li>**용량 정보:** {@link LocationCapacity}를 통해 로케이션의 최대 보관 용량(부피, 무게)을 정의하여 효율적인 공간 활용을 지원합니다.</li>
 *     <li>**로케이션 상태 관리:** {@link LocationStatus}를 통해 로케이션의 현재 상태(예: 사용 가능, 사용 중, 잠김, 비활성화)를 추적합니다.</li>
 * </ul>
 *
 * {@code Location}은 {@link Warehouse} 애그리거트의 하위 개념이지만, 독립적인 일관성 경계를 가지는 애그리거트 루트입니다.
 * 이는 로케이션 자체의 속성 및 상태 변경이 {@code Warehouse}의 변경 없이 독립적으로 관리될 수 있음을 의미합니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Location {
    private LocationId id;
    private final WarehouseId warehouseId;
    private final LocationCode locationCode;
    private final LocationType type;
    private final LocationCapacity capacity;
    private final LocationStatus status;

    @Builder(access = AccessLevel.PACKAGE)
    private Location(
            LocationId id,
            WarehouseId warehouseId,
            LocationCode locationCode,
            LocationType type,
            LocationCapacity capacity,
            LocationStatus status) {
        this.id = id;
        this.warehouseId = DomainValidator.requireNonNull(warehouseId, () -> new InvalidLocationException("창고 ID는 필수입니다."));
        this.locationCode = DomainValidator.requireNonNull(locationCode, () -> new InvalidLocationException("로케이션 코드는 필수입니다."));
        this.type = DomainValidator.requireNonNull(type, () -> new InvalidLocationException("로케이션 유형은 필수입니다."));
        this.capacity = DomainValidator.requireNonNull(capacity, () -> new InvalidLocationException("로케이션 용량 정보는 필수입니다."));
        this.status = DomainValidator.requireNonNull(status, () -> new InvalidLocationException("로케이션 상태는 필수입니다."));
    }

    public void updateStatus(LocationStatus newStatus) {
        Objects.requireNonNull(newStatus, "새로운 로케이션 상태는 필수입니다.");
        this.status = newStatus;
    }
}
