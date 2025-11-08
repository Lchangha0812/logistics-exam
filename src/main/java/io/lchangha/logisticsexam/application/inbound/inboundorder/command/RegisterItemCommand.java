package io.lchangha.logisticsexam.application.inbound.inboundorder.command;

import lombok.Builder;

@Builder
public record RegisterItemCommand(
        Long productId,
        Long amount,
        String unit,
        Long unitPrice,
        String lotNumber
) {
}