package io.lchangha.logisticsexam.application.inbound.inboundorder.command;

import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderType; // Import added

import java.time.LocalDateTime;
import java.util.List;


public record RegisterInboundOrderCommand(
        String referenceValue,
        String referenceType,
        String title,
        String description,
        LocalDateTime orderRequestedAt,
        LocalDateTime expectedArrivalAt,
        List<RegisterItem> items
) {
    public record RegisterItem(
            Long productId,
            Long amount,
            String unit,
            Long unitPrice
    ) {
    }
}
