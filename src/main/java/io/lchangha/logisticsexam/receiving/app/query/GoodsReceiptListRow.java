package io.lchangha.logisticsexam.receiving.app.query;

import java.time.LocalDateTime;

public record GoodsReceiptListRow(
        Long id,
        String grnNumber,
        String status,
        String type,
        Long supplierId,
        String supplierName,
        LocalDateTime receivedAt,
        Long poId
) {
}
