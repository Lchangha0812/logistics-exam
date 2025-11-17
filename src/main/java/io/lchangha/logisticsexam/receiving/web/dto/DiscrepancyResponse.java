package io.lchangha.logisticsexam.receiving.web.dto;

import io.lchangha.logisticsexam.receiving.domain.vo.DiscrepancyCode;

public record DiscrepancyResponse(
        DiscrepancyCode code,
        String note
) {
}
