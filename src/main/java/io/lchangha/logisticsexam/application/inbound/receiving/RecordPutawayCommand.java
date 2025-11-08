package io.lchangha.logisticsexam.application.inbound.receiving;

import lombok.Builder;

import java.util.List;

@Builder
public record RecordPutawayCommand(
        long arrivalId,
        long warehouseId,
        List<PutawayDetail> putawayDetails
) {
    @Builder
    public record PutawayDetail(
            long arrivalItemId, // 어떤 도착 품목을
            long locationId,    // 어느 위치에
            long quantity       // 얼마나 적치했는가
    ) {
    }
}
