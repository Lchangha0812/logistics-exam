package io.lchangha.logisticsexam.receiving.web.dto;

import java.util.List;

public record ReceivingVarianceReportResponse(
        List<ReceivingVarianceRowResponse> rows
) {
    public ReceivingVarianceReportResponse {
        rows = rows == null ? List.of() : List.copyOf(rows);
    }
}
