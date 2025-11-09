package io.lchangha.logisticsexam.masterdata.domain.vo;

import org.springframework.util.Assert;

public record Barcode(String value) {
    public Barcode {
        Assert.hasText(value, "바코드 값은 null이거나 비어 있을 수 없습니다.");
    }

    public static Barcode of(String value) {
        return new Barcode(value);
    }
}
