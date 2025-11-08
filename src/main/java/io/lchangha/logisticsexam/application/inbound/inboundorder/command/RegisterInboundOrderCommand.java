package io.lchangha.logisticsexam.application.inbound.inboundorder.command;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record RegisterInboundOrderCommand(
        String referenceValue,
        String referenceType,
        String title,
        String description,
        LocalDateTime orderRequestedAt,
        LocalDateTime expectedArrivalAt,
        List<RegisterItemCommand> items
) {

}
