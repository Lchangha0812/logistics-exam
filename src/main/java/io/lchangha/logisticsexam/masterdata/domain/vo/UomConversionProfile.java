package io.lchangha.logisticsexam.masterdata.domain.vo;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Item의 baseUom을 기준으로 다른 단위들의 변환 비율을 정의하는 프로필.
 */
public record UomConversionProfile(Map<Uom, BigDecimal> factors) {

    public UomConversionProfile {
        Assert.notNull(factors, "변환 비율 맵은 null일 수 없습니다.");
    }

    public BigDecimal getFactorFor(Uom uom) {
        Assert.notNull(uom, "Uom은 null일 수 없습니다.");
        if (!factors.containsKey(uom)) {
            throw new IllegalArgumentException("'" + uom.symbol() + "' 단위에 대한 변환 비율을 찾을 수 없습니다.");
        }
        return factors.get(uom);
    }
}