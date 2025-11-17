package io.lchangha.logisticsexam.receiving.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record GoodsReceiptLineResponse(
        Long lineId,
        Long itemId,
        String uom,
        BigDecimal quantity,
        Long toLocationId,
        LotInfoPayload lotInfo,
        Boolean requiresExpiry,
        Boolean freeLine,
        BigDecimal orderedQty,
        List<LinePoLinkResponse> poLinks,
        DiscrepancyResponse discrepancy
) {
    public GoodsReceiptLineResponse {
        poLinks = poLinks == null ? List.of() : List.copyOf(poLinks);
    }
}
