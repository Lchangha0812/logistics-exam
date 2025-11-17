package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record LineLinkRequest(
        @NotNull Long lineId,
        @NotNull Long poLineId,
        @NotNull @Positive BigDecimal quantity
) {
}
