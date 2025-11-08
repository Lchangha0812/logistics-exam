package io.lchangha.logisticsexam.domain.inbound.receiving.params;

import io.lchangha.logisticsexam.domain.LotNumber;
import io.lchangha.logisticsexam.domain.Quantity;
import io.lchangha.logisticsexam.domain.masterdata.location.vo.LocationId;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;
import lombok.Builder;

@Builder
public record ItemToReceive(
        ProductId productId,
        Quantity arrivedQuantity,
        LotNumber lotNumber,
        LocationId locationId
) {
}
