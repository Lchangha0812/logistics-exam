package io.lchangha.logisticsexam.domain.inbound.inboundorder.params;

import io.lchangha.logisticsexam.domain.LotNumber;
import io.lchangha.logisticsexam.domain.Quantity;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;
import lombok.Builder;

@Builder
public record RegisterItemParam(
        ProductId productId,
        Quantity quantity,
        Long unitPrice,
        LotNumber lotNumber
) {
}