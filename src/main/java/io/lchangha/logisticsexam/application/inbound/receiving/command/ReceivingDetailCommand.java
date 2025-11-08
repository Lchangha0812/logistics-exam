package io.lchangha.logisticsexam.application.inbound.receiving.command;

import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalItemId;
import io.lchangha.logisticsexam.domain.masterdata.location.vo.LocationId;
import lombok.Builder;

@Builder
public record ReceivingDetailCommand(
        ArrivalItemId arrivalItemId, // 어떤 도착 품목을
        LocationId locationId    // 어느 위치에
) {
}


