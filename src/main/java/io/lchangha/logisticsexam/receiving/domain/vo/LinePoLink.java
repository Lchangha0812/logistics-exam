package io.lchangha.logisticsexam.receiving.domain.vo;

import org.springframework.util.Assert;

import java.math.BigDecimal;

/**
 * 입고(GRN) 라인과 구매 발주(PO) 라인 간의 명시적인 수량 매핑을 나타내는 레코드입니다.
 * <p>
 * 하나의 입고 라인이 여러 PO 라인에 걸쳐 있거나, 그 반대의 경우를 표현하는 데 사용될 수 있습니다.
 * </p>
 *
 * @param poLineId 연결된 PO 라인 ID
 * @param quantity 이 연결에 할당된 수량
 */
public record LinePoLink(Long poLineId, BigDecimal quantity) {
    public LinePoLink {
        Assert.notNull(poLineId, "PO 라인 ID는 null일 수 없습니다.");
        Assert.notNull(quantity, "링크 수량은 null일 수 없습니다.");
        Assert.isTrue(quantity.signum() > 0, "링크 수량은 0보다 커야 합니다.");
    }
}

