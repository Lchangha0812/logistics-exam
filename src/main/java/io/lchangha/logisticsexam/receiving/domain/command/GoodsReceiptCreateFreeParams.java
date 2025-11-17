package io.lchangha.logisticsexam.receiving.domain.command;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceiptLine;
import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptNumber;
import io.lchangha.logisticsexam.shared.domain.AuditInfo;
import lombok.Builder;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GoodsReceiptCreateFreeParams(
        Long id,
        GoodsReceiptNumber grnNumber,
        Long supplierId,
        LocalDateTime receivedAt,
        AuditInfo auditInfo,
        List<GoodsReceiptLine> initialLines
) {
    public GoodsReceiptCreateFreeParams {
        Assert.notNull(id, "GRN ID가 null일 수 없습니다.");
        Assert.notNull(grnNumber, "GRN 번호가 null일 수 없습니다.");
        Assert.notNull(receivedAt, "입고 일시가 null일 수 없습니다.");
        Assert.notNull(auditInfo, "감사 정보가 null일 수 없습니다.");
    }
}
