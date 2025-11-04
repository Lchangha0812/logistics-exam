package io.lchangha.logisticsexam.domain.inbound.record;

import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderId;
import java.util.List;

public interface InboundRecordRepository {
    void save(InboundRecord record);
    List<InboundRecord> findByInboundOrderId(InboundOrderId inboundOrderId);
}