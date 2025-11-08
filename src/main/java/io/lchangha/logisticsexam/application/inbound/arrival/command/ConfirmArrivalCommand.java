package io.lchangha.logisticsexam.application.inbound.arrival.command;

import lombok.Builder;

@Builder
public record ConfirmArrivalCommand(
        long inboundOrderId,
        String deliveryNotePhotoKey,
        long supplierId
) {
}