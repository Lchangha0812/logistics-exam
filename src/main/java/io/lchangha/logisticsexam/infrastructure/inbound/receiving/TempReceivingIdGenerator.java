package io.lchangha.logisticsexam.infrastructure.inbound.receiving;

import io.lchangha.logisticsexam.domain.inbound.receiving.contract.ReceivingIdGenerator;
import io.lchangha.logisticsexam.domain.inbound.receiving.vo.ReceivingId;
import io.lchangha.logisticsexam.domain.inbound.receiving.vo.ReceivingItemId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class TempReceivingIdGenerator implements ReceivingIdGenerator {
    private static Iterator<ReceivingId> idIterator;
    private static Iterator<ReceivingItemId> itemIdIterator;

    static {
        List<ReceivingId> ids = new ArrayList<>();
        List<ReceivingItemId> itemIds = new ArrayList<>();
        for (long i = 1; i <= 1000; i++) {
            ids.add(ReceivingId.of(i));
            itemIds.add(ReceivingItemId.of(i));
        }
        idIterator = ids.iterator();
        itemIdIterator = itemIds.iterator();
    }

    @Override
    public ReceivingId generateId() {
        return idIterator.next();
    }

    @Override
    public ReceivingItemId generateItemId() {
        return itemIdIterator.next();
    }
}
