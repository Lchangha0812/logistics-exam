package io.lchangha.logisticsexam.receiving.domain.vo;

import org.springframework.util.Assert;

/**
 * 입고(GRN) 라인의 강력한 타입(Strongly-typed) ID를 나타내는 레코드입니다.
 * <p>
 * 원시 타입(long) 대신 이 레코드를 사용함으로써 타입 안정성을 높이고 코드의 명확성을 개선합니다.
 * </p>
 *
 * @param value 라인 ID 값
 */
public record GoodsReceiptLineId(Long value) {
    public GoodsReceiptLineId {
        Assert.notNull(value, "입고 라인 ID는 null일 수 없습니다.");
    }
}
