package io.lchangha.logisticsexam.masterdata.domain.vo;

import org.springframework.util.Assert;

/**
 * 단위를 나타내는 값 객체(
 * @param name 단위의 이름 ("kilogram")
 * @param symbol 단위 기호 ("kg")
 * @param type 단위 종류
 */
public record Uom(String name, String symbol, UomType type) {
    public Uom {
        Assert.hasText(name, "단위 이름은 null이거나 비어 있을 수 없습니다.");
        Assert.hasText(symbol, "단위 기호는 null이거나 비어 있을 수 없습니다.");
        Assert.notNull(type, "단위 종류는 null일 수 없습니다.");
    }

    public static Uom of(String name, String symbol, UomType type) {
        return new Uom(name, symbol, type);
    }
}
