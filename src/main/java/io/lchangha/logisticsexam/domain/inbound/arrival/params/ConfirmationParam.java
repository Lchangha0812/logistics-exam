package io.lchangha.logisticsexam.domain.inbound.arrival.params;

import io.lchangha.logisticsexam.domain.inbound.arrival.vo.DeliveryNoteNumber;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrder;
import io.lchangha.logisticsexam.domain.masterdata.partner.vo.PartnerId;
import lombok.Builder;

@Builder
public record ConfirmationParam(
        InboundOrder inboundOrder,
        PartnerId supplierId,
        DeliveryNoteNumber deliveryNoteNumber
) {
}