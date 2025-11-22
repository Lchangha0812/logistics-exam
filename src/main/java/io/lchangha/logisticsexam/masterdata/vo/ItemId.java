package io.lchangha.logisticsexam.masterdata.vo;

import io.lchangha.logisticsexam.shared.Require;

public record ItemId(Long value) {
    public ItemId {
        Require.positive(value, "");
    }
}
