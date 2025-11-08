package io.lchangha.logisticsexam.infrastructure.inbound.inboundorder;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrder;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.contract.InboundOrderRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TempInboundOrderRepository implements InboundOrderRepository {
    private Map<InboundOrderId, InboundOrder> inboundOrders = new HashMap<>();

    @Override
    public void save(InboundOrder inboundOrder) {
        inboundOrders.put(inboundOrder.getId(), inboundOrder);
    }

    @Override
    public Optional<InboundOrder> findById(InboundOrderId inboundOrderId) {
        return Optional.ofNullable(inboundOrders.get(inboundOrderId));
    }
}
