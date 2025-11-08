package io.lchangha.logisticsexam.application.inbound.arrival.command;

import lombok.Builder;

import java.util.List;

@Builder
public record ClaimDetail(
        long inboundOrderItemId,
        long actualQuantity,
        String claimType,
        String claimReason,
        List<String> evidencePhotoKeys
) {
}