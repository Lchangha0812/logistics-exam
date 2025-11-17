package io.lchangha.logisticsexam.receiving.domain.command;

import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptLineId;
import io.lchangha.logisticsexam.receiving.domain.vo.LotInfo;
import lombok.Builder;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Builder
public record GoodsReceiptLineFreeLineParams(
        GoodsReceiptLineId id,
        Long itemId,
        BigDecimal quantity,
        String uom,
        Long toLocationId,
        LotInfo lotInfo,
        boolean requiresExpiry
) {
    public GoodsReceiptLineFreeLineParams {
        Assert.notNull(id, "라인 ID가 null일 수 없습니다.");
        Assert.notNull(itemId, "아이템 ID가 null일 수 없습니다.");
        Assert.notNull(quantity, "수량이 null일 수 없습니다.");
        Assert.hasText(uom, "UOM이 비어 있을 수 없습니다.");
        Assert.notNull(toLocationId, "로케이션 ID가 null일 수 없습니다.");
    }
}
