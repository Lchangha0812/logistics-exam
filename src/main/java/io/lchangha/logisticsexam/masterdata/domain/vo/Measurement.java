package io.lchangha.logisticsexam.masterdata.domain.vo;

import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * 측정된 수량과 단위를 함께 나타내는 값 객체
 * @param value 측정된 수량
 * @param uom 사용된 단위
 */
public record Measurement(BigDecimal value, Uom uom) {
    public Measurement {
        Assert.notNull(value, "측정 값은 null일 수 없습니다.");
        Assert.isTrue(value.compareTo(BigDecimal.ZERO) >= 0, "측정 값은 0보다 작을 수 없습니다.");
        Assert.notNull(uom, "단위는 null일 수 없습니다.");
    }

    public static Measurement of(BigDecimal value, Uom uom) {
        return new Measurement(value, uom);
    }

    /**
     * 다른 Measurement와 단위 종류(UomType)가 동일한지 확인합니다.
     * @param other 비교할 다른 Measurement 객체
     * @return 단위 종류가 같으면 true, 아니면 false
     */
    public boolean isSameType(Measurement other) {
        if (other == null) {
            return false;
        }
        return this.uom.type() == other.uom.type();
    }
}
