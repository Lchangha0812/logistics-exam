package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record SetLineLotRequest(
        @NotNull Long lineId,
        @Valid LotInfoPayload lotInfo
) {
}
