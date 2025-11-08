package io.lchangha.logisticsexam.domain.inbound.receiving.params;

import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalId;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;


@Builder
public record RecordReceivingParam(
        ArrivalId arrivalId,
        LocalDateTime receivingDate,
        List<ItemToReceive> itemsToReceive
) {
}
