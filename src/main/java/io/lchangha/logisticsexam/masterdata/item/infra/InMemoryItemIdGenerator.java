package io.lchangha.logisticsexam.masterdata.item.infra;

import io.lchangha.logisticsexam.masterdata.item.domain.contract.ItemIdGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Primary
public class InMemoryItemIdGenerator implements ItemIdGenerator {
    private final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Long next() {
        return sequence.incrementAndGet();
    }
}
