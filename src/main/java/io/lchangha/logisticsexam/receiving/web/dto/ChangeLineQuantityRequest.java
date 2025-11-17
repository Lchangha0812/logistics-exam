package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ChangeLineQuantityRequest(
        @NotNull Long lineId,
        @NotNull BigDecimal newQuantity
) {
}
