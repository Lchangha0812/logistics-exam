package io.lchangha.logisticsexam.id;

import java.util.Objects;

public record InboundItemId(Long value) {
    public InboundItemId {
        Objects.requireNonNull(value, "입고 상품 ID는 필수입니다.");
        if (value <= 0) {
            throw new IllegalArgumentException("입고 상품 ID는 0보다 커야 합니다.");
        }
    }
}