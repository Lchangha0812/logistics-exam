package io.lchangha.logisticsexam.domain.inbound.record;

import io.lchangha.logisticsexam.domain.Quantity;
import io.lchangha.logisticsexam.domain.inbound.arrival.ArrivalId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderId;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * '입고 기록'을 나타내는 불변의 엔티티입니다.
 * 하나의 Arrival에서 발생한 특정 InboundOrder 품목의 입고 결과에 대한 스냅샷입니다.
 * 이 객체는 생성된 후 수정되지 않습니다.
 */
@Getter
@Builder
public class InboundRecord {
    private InboundRecordId id;
    private final InboundOrderId inboundOrderId;
    private final ArrivalId arrivalId;
    private final ProductId productId;
    private final Quantity receivedQuantity;
    private final LocalDateTime timestamp;
}