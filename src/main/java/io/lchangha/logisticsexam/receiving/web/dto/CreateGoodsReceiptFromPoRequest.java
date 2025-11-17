package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateGoodsReceiptFromPoRequest(
        String grnNumber,
        @NotNull Long poId,
        @NotNull Long supplierId,
        @NotNull LocalDateTime receivedAt,
        @Valid List<PoReceiptLineRequest> lines
) {
    public CreateGoodsReceiptFromPoRequest {
        lines = lines == null ? List.of() : List.copyOf(lines);
    }
}
