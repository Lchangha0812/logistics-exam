package io.lchangha.logisticsexam.purchasing.domain.dto;

import io.lchangha.logisticsexam.masterdata.item.domain.vo.Quantity;
import io.lchangha.logisticsexam.masterdata.vo.ItemId;
import io.lchangha.logisticsexam.purchasing.domain.vo.OverReceivePolicy;
import io.lchangha.logisticsexam.purchasing.domain.vo.POLineId;

import java.math.BigDecimal;

public record POLineState(
        POLineId id,
        ItemId itemId,
        Quantity targetQty,
        BigDecimal price,
        OverReceivePolicy overReceivePolicy,
        Quantity receivedQty
) {
}
