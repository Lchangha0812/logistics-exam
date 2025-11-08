package io.lchangha.logisticsexam.domain.inbound.arrival.contract;

import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalId;
import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalItemId;

public interface ArrivalIdGenerator {
    ArrivalId generateId();
    ArrivalItemId generateItemId();
}
