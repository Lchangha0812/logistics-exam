package io.lchangha.logisticsexam.receiving.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "PO 기반 입고 생성 요청")
public record CreateGoodsReceiptFromPoRequest(
        @Schema(description = "발주(PO) ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull Long poId
) {
}

