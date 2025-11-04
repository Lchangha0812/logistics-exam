package io.lchangha.logisticsexam.domain;

import io.lchangha.logisticsexam.domain.masterdata.partner.UnitOfMeasure;

public record Quantity(long amount, UnitOfMeasure unit) {
    public Quantity {
        DomainValidator.isTrue(amount >= 0, () -> new IllegalArgumentException("수량은 0 이상이어야 합니다."));
        DomainValidator.requireNonNull(unit, () -> new IllegalArgumentException("단위는 필수입니다."));
    }

    public static Quantity zero(UnitOfMeasure unit) {
        return new Quantity(0, unit);
    }

    public Quantity add(Quantity other) {
        if (!isSameUnit(other)) {
            throw new IllegalArgumentException("단위가 다른 수량은 더할 수 없습니다.");
        }
        return new Quantity(this.amount + other.amount, this.unit);
    }

    public boolean isGreaterThan(Quantity other) {
        if (!isSameUnit(other)) {
            throw new IllegalArgumentException("단위가 다른 수량은 비교할 수 없습니다.");
        }
        return this.amount > other.amount;
    }

    public boolean isSameUnit(Quantity other) {
        return this.unit.equals(other.unit);
    }

    public boolean isGreaterThanOrEqual(Quantity other) {
        if (!isSameUnit(other)) {
            throw new IllegalArgumentException("단위가 다른 수량은 비교할 수 없습니다.");
        }
        return this.amount >= other.amount;
    }
}
