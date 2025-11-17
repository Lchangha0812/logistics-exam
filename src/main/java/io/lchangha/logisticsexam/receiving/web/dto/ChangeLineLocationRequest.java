package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.constraints.NotNull;

public record ChangeLineLocationRequest(
        @NotNull Long lineId,
        @NotNull Long newLocationId
) {
}
