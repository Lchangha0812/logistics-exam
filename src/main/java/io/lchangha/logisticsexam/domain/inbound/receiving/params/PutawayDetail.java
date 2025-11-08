package io.lchangha.logisticsexam.domain.inbound.receiving.params;

import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalItemId;
import io.lchangha.logisticsexam.domain.masterdata.location.vo.LocationId;
import lombok.Builder;

@Builder
public record PutawayDetail(
        ArrivalItemId arrivalItemId,
        LocationId locationId
) {
}
