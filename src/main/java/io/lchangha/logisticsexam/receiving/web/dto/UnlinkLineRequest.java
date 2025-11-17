package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.constraints.NotNull;

public record UnlinkLineRequest(
        @NotNull Long lineId,
        @NotNull Long poLineId
) {
}
