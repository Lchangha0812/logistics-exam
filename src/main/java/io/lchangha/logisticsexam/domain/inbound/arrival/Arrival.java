package io.lchangha.logisticsexam.domain.inbound.arrival;

import io.lchangha.logisticsexam.domain.DomainValidator;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderId;
import io.lchangha.logisticsexam.domain.masterdata.partner.PartnerId;
import io.lchangha.logisticsexam.domain.inbound.exception.InvalidArrivalException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code Arrival} 애그리거트 루트는 창고에 화물이 물리적으로 도착한 단일 이벤트를 나타냅니다.
 * 이 애그리거트의 책임은 '납품서'를 기반으로 '실제 도착한 상품과 수량(관찰된 수량)'을 확정하고 기록하는 것입니다.
 * 생성된 후에는 수정되지 않는 불변의 사실을 기록합니다.
 * 검품(Inspection) 프로세스는 이 애그리거트가 생성된 후에 별개의 프로세스로 시작됩니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Arrival {
    private ArrivalId id;
    private final InboundOrderId inboundOrderId;
    private final LocalDateTime actualArrivalTime;
    private final DeliveryNoteNumber deliveryNoteNumber;
    private final PartnerId supplierId;
    private final List<ArrivalItem> arrivalItems = new ArrayList<>();
    private final ArrivalStatus status;

    @Builder(access = AccessLevel.PACKAGE)
    private Arrival(
            ArrivalId id,
            InboundOrderId inboundOrderId,
            LocalDateTime actualArrivalTime,
            DeliveryNoteNumber deliveryNoteNumber,
            PartnerId supplierId,
            List<ArrivalItem> arrivalItems) {
        this.id = id;
        this.inboundOrderId = DomainValidator.requireNonNull(inboundOrderId, () -> new InvalidArrivalException("입고 주문 ID는 필수입니다."));
        this.actualArrivalTime = DomainValidator.requireNonNull(actualArrivalTime, () -> new InvalidArrivalException("실제 도착 시간은 필수입니다."));
        this.deliveryNoteNumber = DomainValidator.requireNonNull(deliveryNoteNumber, () -> new InvalidArrivalException("납품서 번호는 필수입니다."));
        this.supplierId = DomainValidator.requireNonNull(supplierId, () -> new InvalidArrivalException("공급업체 ID는 필수입니다."));
        DomainValidator.requireNonEmpty(arrivalItems, () -> new InvalidArrivalException("입하 상품은 필수입니다."));
        arrivalItems.forEach(this::addArrivalItem);
        this.status = ArrivalStatus.COMPLETED; // 생성 즉시 완료 상태
    }

    public List<ArrivalItem> getArrivalItems() {
        return Collections.unmodifiableList(arrivalItems);
    }

    private void addArrivalItem(ArrivalItem arrivalItem) {
        arrivalItems.add(arrivalItem);
    }
}
