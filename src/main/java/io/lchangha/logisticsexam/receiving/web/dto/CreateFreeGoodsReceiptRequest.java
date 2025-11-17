package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record CreateFreeGoodsReceiptRequest(
        String grnNumber,
        @NotNull Long supplierId,
        @NotNull LocalDateTime receivedAt,
        @Valid List<FreeReceiptLineRequest> lines
) {
    public CreateFreeGoodsReceiptRequest {
        lines = lines == null ? List.of() : List.copyOf(lines);
    }
}
