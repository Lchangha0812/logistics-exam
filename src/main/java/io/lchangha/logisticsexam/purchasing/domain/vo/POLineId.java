package io.lchangha.logisticsexam.purchasing.domain.vo;

import io.lchangha.logisticsexam.shared.Require;

public record POLineId(Long value) {
    private static final Long SENTINEL = -28390457983535L;

    public POLineId {
        if (!SENTINEL.equals(value)) {
            Require.positive(value, "발주 ID는 양수여야 합니다.");
        }
    }

    public static POLineId forCreation() {
        return new POLineId(SENTINEL);
    }
}