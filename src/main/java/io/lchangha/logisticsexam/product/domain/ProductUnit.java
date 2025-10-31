package io.lchangha.logisticsexam.product.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.domain.UnitOfMeasure;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record ProductUnit(
        UnitOfMeasure baseUnit,
        UnitOfMeasure packagingUnit,
        Long quantityPerBaseUnit,
        Long quantityPerPackagingUnit
) {
    public ProductUnit {
        DomainValidator.requireNonNull(baseUnit, () -> new DomainException("기본 단위는 필수입니다."));
        DomainValidator.requireNonNull(packagingUnit, () -> new DomainException("포장 단위는 필수입니다."));

        DomainValidator.requireNonNull(quantityPerBaseUnit, () -> new DomainException("기본 단위당 수량은 필수입니다."));
        DomainValidator.isTrue(quantityPerBaseUnit > 0, () -> new DomainException("기본 단위당 수량은 0보다 커야 합니다."));

        DomainValidator.requireNonNull(quantityPerPackagingUnit, () -> new DomainException("포장 단위당 수량은 필수입니다."));
        DomainValidator.isTrue(quantityPerPackagingUnit > 0, () -> new DomainException("포장 단위당 수량은 0보다 커야 합니다."));
    }

    public static ProductUnit of(UnitOfMeasure baseUnit, UnitOfMeasure packagingUnit, Long quantityPerBaseUnit, Long quantityPerPackagingUnit) {
        return new ProductUnit(baseUnit, packagingUnit, quantityPerBaseUnit, quantityPerPackagingUnit);
    }
}