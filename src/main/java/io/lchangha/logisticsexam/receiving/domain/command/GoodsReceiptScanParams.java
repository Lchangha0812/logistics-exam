package io.lchangha.logisticsexam.receiving.domain.command;

import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptLineId;
import io.lchangha.logisticsexam.receiving.domain.vo.LotInfo;
import lombok.Builder;
import org.springframework.util.Assert;

import java.math.BigDecimal;

@Builder
public record GoodsReceiptScanParams(
        GoodsReceiptLineId newLineId,
        Long itemId,
        BigDecimal incrementQty,
        String uom,
        Long locationId,
        LotInfo lotInfo,
        Long poLineId,
        BigDecimal orderedQty,
        boolean requiresExpiry
) {
    public GoodsReceiptScanParams {
        Assert.notNull(newLineId, "스캔용 라인 ID가 null일 수 없습니다.");
        Assert.notNull(itemId, "아이템 ID가 null일 수 없습니다.");
        Assert.notNull(incrementQty, "증가 수량이 null일 수 없습니다.");
        Assert.isTrue(incrementQty.signum() > 0, "증가 수량은 0보다 커야 합니다.");
        Assert.hasText(uom, "UOM이 비어 있을 수 없습니다.");
        Assert.notNull(locationId, "로케이션 ID가 null일 수 없습니다.");
        if (poLineId != null) {
            Assert.notNull(orderedQty, "PO 라인 스캔은 발주 수량 정보가 필요합니다.");
        }
    }
}
