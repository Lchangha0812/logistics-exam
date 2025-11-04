package io.lchangha.logisticsexam.infrasturcture.inbound.inboundorder;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrder;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TempInboundOrderRepository implements InboundOrderRepository {
    private Map<InboundOrderId, InboundOrder> inboundOrders = new HashMap<>();

    @Override
    public void save(InboundOrder inboundOrder) {
        inboundOrders.put(inboundOrder.getId(), inboundOrder);
    }
}
