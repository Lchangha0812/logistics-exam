package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record AddPoLineRequest(
        @NotNull Long itemId,
        @NotNull Long poLineId,
        @NotNull BigDecimal quantity,
        @NotNull BigDecimal orderedQty,
        @NotBlank String uom,
        @NotNull Long toLocationId,
        @Valid LotInfoPayload lotInfo,
        @NotNull Boolean requiresExpiry
) {
}
