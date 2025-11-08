package io.lchangha.logisticsexam.application.inbound.receiving.command;

import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalId;
import lombok.Builder;

import java.util.List;

@Builder
public record RecordReceivingCommand(
        ArrivalId arrivalId,
        List<ReceivingDetailCommand> receivingDetailCommands
) {
}