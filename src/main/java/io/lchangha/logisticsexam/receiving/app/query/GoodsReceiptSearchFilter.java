package io.lchangha.logisticsexam.receiving.app.query;

import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptStatus;

import java.time.LocalDate;

public record GoodsReceiptSearchFilter(
        LocalDate fromDate,
        LocalDate toDate,
        Long supplierId,
        Long poId,
        Long itemId,
        GoodsReceiptStatus status,
        int page,
        int size
) {
}
