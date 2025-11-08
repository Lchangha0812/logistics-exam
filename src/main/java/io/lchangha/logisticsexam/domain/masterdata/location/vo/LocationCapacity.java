package io.lchangha.logisticsexam.domain.masterdata.location.vo;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.DomainValidator;

public record LocationCapacity(
        Long maxVolume,
        Long maxWeight
) {
    public LocationCapacity {
        DomainValidator.requireNonNull(maxVolume, () -> new DomainException("최대 부피는 필수입니다."));
        DomainValidator.isTrue(maxVolume > 0, () -> new DomainException("최대 부피는 0보다 커야 합니다."));

        DomainValidator.requireNonNull(maxWeight, () -> new DomainException("최대 중량은 필수입니다."));
        DomainValidator.isTrue(maxWeight > 0, () -> new DomainException("최대 중량은 0보다 커야 합니다."));
    }

    public static LocationCapacity of(Long maxVolume, Long maxWeight) {
        return new LocationCapacity(maxVolume, maxWeight);
    }
}
