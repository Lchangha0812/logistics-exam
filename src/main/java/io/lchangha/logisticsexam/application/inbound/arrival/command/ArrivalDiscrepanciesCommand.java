package io.lchangha.logisticsexam.application.inbound.arrival.command;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record ArrivalDiscrepanciesCommand(
        long inboundOrderId,
        LocalDateTime actualArrivalTime,
        String deliveryNotePhotoKey,
        long supplierId,
        List<ClaimDetail> claims
) {
}
