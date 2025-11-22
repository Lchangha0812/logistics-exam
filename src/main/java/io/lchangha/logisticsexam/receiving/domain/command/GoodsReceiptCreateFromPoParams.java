package io.lchangha.logisticsexam.receiving.domain.command;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceiptLine;
import io.lchangha.logisticsexam.receiving.domain.PoTolerancePolicy;
import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptNumber;
import lombok.Builder;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GoodsReceiptCreateFromPoParams(
        Long id,
        GoodsReceiptNumber grnNumber,
        Long supplierId,
        Long poId,
        LocalDateTime receivedAt,
        PoTolerancePolicy tolerancePolicy,
        List<GoodsReceiptLine> initialLines
) {
    public GoodsReceiptCreateFromPoParams {
        Assert.notNull(id, "GRN ID가 null일 수 없습니다.");
        Assert.notNull(grnNumber, "GRN 번호가 null일 수 없습니다.");
        Assert.notNull(supplierId, "공급사 ID가 null일 수 없습니다.");
        Assert.notNull(poId, "PO ID가 null일 수 없습니다.");
        Assert.notNull(receivedAt, "입고 일시가 null일 수 없습니다.");
    }
}
