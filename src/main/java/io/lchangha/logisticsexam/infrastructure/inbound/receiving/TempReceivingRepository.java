package io.lchangha.logisticsexam.infrastructure.inbound.receiving;

import io.lchangha.logisticsexam.domain.inbound.receiving.Receiving;
import io.lchangha.logisticsexam.domain.inbound.receiving.contract.ReceivingRepository;
import io.lchangha.logisticsexam.domain.inbound.receiving.vo.ReceivingId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TempReceivingRepository implements ReceivingRepository {
    private final Map<ReceivingId, Receiving> receivings = new HashMap<>();

    @Override
    public void save(Receiving receiving) {
        receivings.put(receiving.getId(), receiving);
    }

    @Override
    public Optional<Receiving> findById(ReceivingId receivingId) {
        return Optional.ofNullable(receivings.get(receivingId));
    }
}
