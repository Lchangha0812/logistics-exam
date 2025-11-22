package io.lchangha.logisticsexam.masterdata.item.domain;

import io.lchangha.logisticsexam.masterdata.item.domain.vo.*;
import io.lchangha.logisticsexam.masterdata.vo.TemperatureZone;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
public class Item {
    private Long id;
    private SKU sku;
    private String name;
    private Uom baseUom;
    private ItemCategory itemCategory;
    private TemperatureZone temperatureZone;
    private boolean requiresExpiry;
    private BigDecimal safetyStock;
    private boolean active;

    private UomConversionProfile uomConversionProfile;

    @Builder
    private Item(Long id, String name, SKU sku, Uom baseUom, ItemCategory itemCategory,
                 TemperatureZone temperatureZone, boolean requiresExpiry, BigDecimal safetyStock,
                 boolean active, UomConversionProfile uomConversionProfile) {

        Assert.hasText(name, "아이템 이름은 비어 있을 수 없습니다.");
        Assert.notNull(sku, "아이템 SKU는 null일 수 없습니다.");
        Assert.notNull(baseUom, "아이템 기본 단위(baseUom)는 null일 수 없습니다.");
        Assert.notNull(uomConversionProfile, "아이템 단위 변환 프로필은 null일 수 없습니다.");

        this.id = id;
        this.name = name;
        this.sku = sku;
        this.baseUom = baseUom;
        this.itemCategory = itemCategory;
        this.temperatureZone = temperatureZone;
        this.requiresExpiry = requiresExpiry;
        this.safetyStock = safetyStock;
        this.active = active;
        this.uomConversionProfile = uomConversionProfile;
    }

    /**
     * 이 아이템의 단위 변환 규칙에 따라 Measurement를 다른 단위로 변환합니다.
     *
     * @param quantity 변환할 측정 객체
     * @param targetUom   변환 목표 단위
     * @return 변환된 Quantity 객체
     * @throws IllegalArgumentException      변환이 불가능하거나 필요한 비율 정보가 없을 경우
     * @throws UnsupportedOperationException 보편적 변환이 지원되지 않는 경우
     */
    public Quantity convert(Quantity quantity, Uom targetUom) {
        Assert.notNull(quantity, "측정 객체는 null일 수 없습니다.");
        Assert.notNull(targetUom, "목표 단위는 null일 수 없습니다.");
        Assert.notNull(this.baseUom, "아이템의 기본 단위(baseUom)는 null일 수 없습니다.");

        Uom fromUom = quantity.uom();

        // 변환이 필요 없는 경우
        if (fromUom.equals(targetUom)) {
            return quantity;
        }

        // 타입이 같고, COUNT가 아닌 경우 (WEIGHT, VOLUME)에만 보편적 변환 시도
        if (fromUom.type() == targetUom.type() && fromUom.type() != Uom.Type.COUNT) {
            return quantity.convertWithinSameType(targetUom);
        }

        // 그 외 모든 경우
        BigDecimal fromFactor = fromUom.equals(this.baseUom)
                ? BigDecimal.ONE
                : uomConversionProfile.getFactorFor(fromUom);

        BigDecimal toFactor = targetUom.equals(this.baseUom)
                ? BigDecimal.ONE
                : uomConversionProfile.getFactorFor(targetUom);

        // 4. 최종 값 계산
        // TODO: 반올림 아이템 따라 정책 다르게 하는거 고려 해봐야함
        BigDecimal valueInBase = quantity.value().multiply(fromFactor);
        BigDecimal newValue = valueInBase.divide(toFactor, 5, RoundingMode.HALF_UP);

        return new Quantity(newValue, targetUom);
    }
}
