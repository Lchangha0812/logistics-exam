package io.lchangha.logisticsexam.domain.inbound.receiving.contract;

import io.lchangha.logisticsexam.domain.inbound.receiving.vo.ReceivingId;
import io.lchangha.logisticsexam.domain.inbound.receiving.vo.ReceivingItemId;

public interface ReceivingIdGenerator {
    ReceivingId generateId();
    ReceivingItemId generateItemId();
}
