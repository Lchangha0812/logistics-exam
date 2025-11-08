package io.lchangha.logisticsexam.infrastructure.inbound.arrival;

import io.lchangha.logisticsexam.domain.inbound.arrival.contract.ArrivalIdGenerator;
import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalId;
import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalItemId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class TempArrivalIdGenerator implements ArrivalIdGenerator {
    private static Iterator<ArrivalId> idIterator;
    private static Iterator<ArrivalItemId> itemIdIterator;

    static {
        List<ArrivalId> ids = new ArrayList<>();
        List<ArrivalItemId> itemIds = new ArrayList<>();
        for (long i = 1; i <= 1000; i++) {
            ids.add(ArrivalId.of(i));
            itemIds.add(ArrivalItemId.of(i));
        }
        idIterator = ids.iterator();
        itemIdIterator = itemIds.iterator();
    }


    @Override
    public ArrivalId generateId() {
        return idIterator.next();
    }

    @Override
    public ArrivalItemId generateItemId() {
        return itemIdIterator.next();
    }
}
