package io.lchangha.logisticsexam.application.inbound.contract;

import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderItemId;

public interface InboundOrderIdGenerator {
    InboundOrderId nextOrderId();
    InboundOrderItemId nextItemId();
}
