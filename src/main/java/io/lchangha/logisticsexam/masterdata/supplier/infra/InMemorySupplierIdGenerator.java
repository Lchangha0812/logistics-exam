package io.lchangha.logisticsexam.masterdata.supplier.infra;

import io.lchangha.logisticsexam.masterdata.supplier.domain.contract.SupplierIdGenerator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
@Primary
public class InMemorySupplierIdGenerator implements SupplierIdGenerator {
    private final AtomicLong sequence = new AtomicLong(0L);

    @Override
    public Long next() {
        return sequence.incrementAndGet();
    }
}
