package io.lchangha.logisticsexam.domain.inbound.arrival.contract;

import io.lchangha.logisticsexam.domain.inbound.arrival.Arrival;
import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalId;

import java.util.Optional;

public interface ArrivalRepository {
    void save(Arrival arrival);

    Optional<Arrival> findById(ArrivalId arrivalId);
}
