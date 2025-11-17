package io.lchangha.logisticsexam.receiving.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * PO 기반 입고 시 적용되는 허용 오차 정책을 정의하는 클래스입니다.
 * <p>
 * 이 정책은 발주 수량 대비 입고 수량의 과입고/과소입고 허용 범위를 결정하고,
 * PO에 없는 품목의 입고 가능 여부를 제어합니다.
 * </p>
 */
@Getter
public class PoTolerancePolicy {
    private final Long id;
    private final BigDecimal overPercentage; // 과입고 허용바율
    private final BigDecimal underPercentage; // 과소입고
    private final boolean allowExtraItems; //po 에 없어도 허용할건지

    @Builder
    private PoTolerancePolicy(Long id, BigDecimal overPercentage, BigDecimal underPercentage, boolean allowExtraItems) {
        this.id = id;
        this.overPercentage = normalize(overPercentage);
        this.underPercentage = normalize(underPercentage);
        this.allowExtraItems = allowExtraItems;
    }

    public static PoTolerancePolicy reconstitute(Long id, BigDecimal overPercentage, BigDecimal underPercentage, boolean allowExtraItems) {
        return new PoTolerancePolicy(id, overPercentage, underPercentage, allowExtraItems);
    }

    private BigDecimal normalize(BigDecimal value) {
        if (value == null) {
            return BigDecimal.ZERO;
        }
        Assert.isTrue(value.signum() >= 0, "허용 오차율은 음수일 수 없습니다.");
        return value.setScale(4, RoundingMode.HALF_UP);
    }

    /**
     * 주어진 입고 수량이 발주 수량 대비 허용 오차 범위 내에 있는지 확인합니다.
     *
     * @param orderedQty  발주 수량
     * @param receivedQty 입고 수량
     * @return 허용 범위 내에 있으면 true, 아니면 false
     */
    public boolean isWithinTolerance(BigDecimal orderedQty, BigDecimal receivedQty) {
        boolean isNotApplicable = orderedQty == null || orderedQty.signum() <= 0;
        if (isNotApplicable) {
            return true; // 발주 수량이 없거나 0 이하면 검사하지 않음
        }
        BigDecimal overLimit = orderedQty.multiply(BigDecimal.ONE.add(overPercentage));
        BigDecimal underLimit = orderedQty.multiply(BigDecimal.ONE.subtract(underPercentage));

        boolean isWithinLowerBound = receivedQty.compareTo(underLimit) >= 0;
        boolean isWithinUpperBound = receivedQty.compareTo(overLimit) <= 0;

        return isWithinLowerBound && isWithinUpperBound;
    }
}
