package io.lchangha.logisticsexam.domain.inbound.inboundorder.contract;

import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrder;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderId;


import java.util.Optional;

public interface InboundOrderRepository {
    /**
     * InboundOrder 를 영속화(저장)합니다.
     *
     * @param inboundOrder 저장할 InboundOrder
     */
    void save(InboundOrder inboundOrder);

    Optional<InboundOrder> findById(InboundOrderId inboundOrderId);
}
