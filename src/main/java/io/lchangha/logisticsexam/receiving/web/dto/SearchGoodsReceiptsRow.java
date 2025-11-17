package io.lchangha.logisticsexam.receiving.web.dto;

import java.time.LocalDateTime;

public record SearchGoodsReceiptsRow(
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
