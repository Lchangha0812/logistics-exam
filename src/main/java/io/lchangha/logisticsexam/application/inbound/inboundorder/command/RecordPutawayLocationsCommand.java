package io.lchangha.logisticsexam.application.inbound.inboundorder.command;

import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderItemId;
import io.lchangha.logisticsexam.domain.masterdata.location.vo.LocationId;
import lombok.Builder;

import java.util.List;

@Builder
public record RecordPutawayLocationsCommand(
        InboundOrderId inboundOrderId,
        List<PutawayDetail> putawayDetails
) {
    @Builder
    public record PutawayDetail(
            InboundOrderItemId inboundOrderItemId,
            LocationId locationId
    ) {
    }
}
