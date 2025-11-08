package io.lchangha.logisticsexam.infrastructure.inbound.inboundorder;

import io.lchangha.logisticsexam.domain.inbound.inboundorder.contract.InboundOrderIdGenerator;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderItemId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;


@Component
public class TempInboundOrderGenerator implements InboundOrderIdGenerator {
    private static Iterator<InboundOrderId> orderIdIterator;
    private static Iterator<InboundOrderItemId> itemIdIterator;
    static {
        var orderIds = new ArrayList<InboundOrderId>();
        var itemIds = new ArrayList<InboundOrderItemId>();
        // 대략 1000까지?
        for  (long i = 1; i <= 1000; i++) {
            orderIds.add(InboundOrderId.of(i));
            itemIds.add(InboundOrderItemId.of(i));
        }
        orderIdIterator = orderIds.iterator();
        itemIdIterator = itemIds.iterator();
    }

    @Override
    public InboundOrderId generateId() {
        return orderIdIterator.next();
    }

    @Override
    public InboundOrderItemId generateItemId() {
        return itemIdIterator.next();
    }
}
