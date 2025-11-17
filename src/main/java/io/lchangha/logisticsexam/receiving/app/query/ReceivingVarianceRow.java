package io.lchangha.logisticsexam.receiving.app.query;

import java.math.BigDecimal;

public record ReceivingVarianceRow(
        Long supplierId,
        String supplierName,
        Long itemId,
        String itemName,
        Long warehouseId,
        BigDecimal overQty,
        BigDecimal underQty,
        BigDecimal extraQty,
        BigDecimal substituteQty
) {
}
