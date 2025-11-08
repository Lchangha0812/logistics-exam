package io.lchangha.logisticsexam.infrastructure.inbound.arrival;

import io.lchangha.logisticsexam.domain.inbound.arrival.Arrival;
import io.lchangha.logisticsexam.domain.inbound.arrival.contract.ArrivalRepository;
import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TempArrivalRepository implements ArrivalRepository {
    private final Map<ArrivalId, Arrival> arrivals = new HashMap<>();

    @Override
    public void save(Arrival arrival) {
        arrivals.put(arrival.getId(), arrival);
    }

    @Override
    public Optional<Arrival> findById(ArrivalId arrivalId) {
        return Optional.ofNullable(arrivals.get(arrivalId));
    }
}
