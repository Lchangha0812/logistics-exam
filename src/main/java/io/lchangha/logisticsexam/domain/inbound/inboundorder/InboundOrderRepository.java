package io.lchangha.logisticsexam.domain.inbound.inboundorder;

public interface InboundOrderRepository {
    /**
     * InboundOrder 애그리거트를 영속화(저장)합니다.
     *
     * @param inboundOrder 저장할 InboundOrder 애그리거트
     */
    void save(InboundOrder inboundOrder);
}
