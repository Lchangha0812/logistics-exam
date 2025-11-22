package io.lchangha.logisticsexam.masterdata.supplier.domain.vo;

import io.lchangha.logisticsexam.shared.Require;

public record SupplierId(Long value ) {

    public SupplierId {
        Require.positive(value, "TODO: 적당한 메세지");
    }
}
