package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record ReceivingVarianceReportRequest(
        @NotNull LocalDate fromDate,
        @NotNull LocalDate toDate,
        Long supplierId,
        Long itemId,
        Long warehouseId
) {
    public ReceivingVarianceReportRequest {
        if (fromDate.isAfter(toDate)) {
            throw new IllegalArgumentException("fromDate는 toDate보다 이후일 수 없습니다.");
        }
    }
}
