package io.lchangha.logisticsexam.receiving.app.query;

import java.time.LocalDate;

public record ReceivingVarianceFilter(
        LocalDate fromDate,
        LocalDate toDate,
        Long supplierId,
        Long itemId,
        Long warehouseId
) {
}
