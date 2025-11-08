package io.lchangha.logisticsexam.domain.inbound.receiving.contract;

import io.lchangha.logisticsexam.domain.inbound.receiving.Receiving;
import io.lchangha.logisticsexam.domain.inbound.receiving.vo.ReceivingId;

import java.util.Optional;

public interface ReceivingRepository {
    void save(Receiving receiving);

    Optional<Receiving> findById(ReceivingId receivingId);
}
