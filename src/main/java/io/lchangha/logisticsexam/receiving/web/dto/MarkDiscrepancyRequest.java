package io.lchangha.logisticsexam.receiving.web.dto;

import io.lchangha.logisticsexam.receiving.domain.vo.DiscrepancyCode;
import jakarta.validation.constraints.NotNull;

public record MarkDiscrepancyRequest(
        @NotNull Long lineId,
        @NotNull DiscrepancyCode discrepancyCode,
        String note
) {
}
