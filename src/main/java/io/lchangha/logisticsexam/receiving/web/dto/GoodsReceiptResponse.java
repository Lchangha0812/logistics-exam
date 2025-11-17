package io.lchangha.logisticsexam.receiving.web.dto;

import java.time.LocalDateTime;
import java.util.List;

public record GoodsReceiptResponse(
        Long id,
        String grnNumber,
        String status,
        String type,
        Long supplierId,
        Long poId,
        LocalDateTime receivedAt,
        List<GoodsReceiptLineResponse> lines
) {
    public GoodsReceiptResponse {
        lines = lines == null ? List.of() : List.copyOf(lines);
    }
}
