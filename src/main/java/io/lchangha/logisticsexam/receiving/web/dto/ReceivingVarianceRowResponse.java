package io.lchangha.logisticsexam.receiving.web.dto;

import java.math.BigDecimal;

public record ReceivingVarianceRowResponse(
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
