package io.lchangha.logisticsexam.masterdata.location.infra;

import io.lchangha.logisticsexam.masterdata.location.domain.contract.LocationIdGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Primary
public class InMemoryLocationIdGenerator implements LocationIdGenerator {
    private final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Long next() {
        return sequence.incrementAndGet();
    }
}
