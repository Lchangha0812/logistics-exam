package io.lchangha.logisticsexam.masterdata.domain.vo;

import org.springframework.util.Assert;

public record SKU(String value) {
    public SKU {
        Assert.hasText(value, "SKU 값은 null이거나 비어 있을 수 없습니다.");
    }

    public static SKU of(String value) {
        return new SKU(value);
    }
}
