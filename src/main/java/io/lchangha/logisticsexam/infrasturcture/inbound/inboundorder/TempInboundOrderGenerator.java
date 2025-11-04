package io.lchangha.logisticsexam.infrasturcture.inbound.inboundorder;

import io.lchangha.logisticsexam.application.inbound.contract.InboundOrderIdGenerator;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderItemId;
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
        for  (int i = 0; i < 1000; i++) {
            orderIds.add(InboundOrderId.of((long) i));
            itemIds.add(InboundOrderItemId.of((long) i));
        }
        orderIdIterator = orderIds.iterator();
        itemIdIterator = itemIds.iterator();
    }

    @Override
    public InboundOrderId nextOrderId() {
        return orderIdIterator.next();
    }

    @Override
    public InboundOrderItemId nextItemId() {
        return itemIdIterator.next();
    }
}
