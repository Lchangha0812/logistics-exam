package io.lchangha.logisticsexam.domain.inbound.inboundorder.contract;

import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderItemId;

public interface InboundOrderIdGenerator {
    InboundOrderId generateId();
    InboundOrderItemId generateItemId();
}
