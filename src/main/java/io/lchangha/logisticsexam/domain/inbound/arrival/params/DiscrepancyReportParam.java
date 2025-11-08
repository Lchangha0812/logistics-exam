package io.lchangha.logisticsexam.domain.inbound.arrival.params;

import io.lchangha.logisticsexam.application.inbound.arrival.command.ClaimDetail;
import io.lchangha.logisticsexam.domain.inbound.arrival.vo.DeliveryNoteNumber;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrder;
import io.lchangha.logisticsexam.domain.masterdata.partner.vo.PartnerId;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DiscrepancyReportParam(
        InboundOrder inboundOrder,
        PartnerId supplierId,
        DeliveryNoteNumber deliveryNoteNumber,
        LocalDateTime actualArrivalTime,
        List<ClaimDetail> claims
) {
}