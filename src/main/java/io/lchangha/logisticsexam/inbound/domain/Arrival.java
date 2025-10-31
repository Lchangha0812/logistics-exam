package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.common.util.DomainValidator;
import io.lchangha.logisticsexam.id.ArrivalId;
import io.lchangha.logisticsexam.id.InboundOrderId;
import io.lchangha.logisticsexam.id.PartnerId;
import io.lchangha.logisticsexam.inbound.domain.DeliveryNoteNumber;
import io.lchangha.logisticsexam.inbound.exception.InvalidArrivalException;
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
 * {@code Arrival} 애그리거트 루트는 창고로 들어오는 화물의 물리적인 도착 및 초기 검수 과정을 관리합니다.
 * {@link InboundOrder}를 기반으로 실제 화물이 도착하고, 납품서와 대조하여 검품 대기 상태가 되기까지의 흐름을 나타냅니다.
 * 이는 WMS의 입고 프로세스 중 '입하' 및 '검품' 단계에 해당합니다.
 *
 * 이 애그리거트는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**입하 식별:** {@link ArrivalId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**입고 주문 참조:** {@link InboundOrderId}를 참조하여 어떤 입고 주문에 대한 입하인지 연결합니다.</li>
 *     <li>**입하 정보:** 실제 도착 시간, 납품서 번호, 공급업체 정보 등 입하의 기본적인 정보를 정의합니다.</li>
 *     <li>**입하 상품 관리:** {@link ArrivalItem} 목록을 관리하며, 각 상품별 도착 수량 및 검수 결과를 포함합니다.</li>
 *     <li>**입하 상태 관리:** {@link ArrivalStatus}를 통해 입하의 현재 진행 상태(예: 예정됨, 도착 완료, 검품 중, 처리 완료, 거절됨)를 추적합니다.</li>
 *     <li>**데이터 일관성 보장:** 입하와 관련된 모든 비즈니스 규칙 및 상태 전이 로직은 이 애그리거트 내에서 관리되어 데이터의 일관성을 보장합니다.</li>
 * </ul>
 *
 * {@code Arrival}은 {@link InboundOrder}와는 다른 일관성 경계를 가지며, 물리적인 도착과 초기 검수 단계의 불변성을 책임집니다.
 * {@link ArrivalItem}은 {@code Arrival} 애그리거트의 일부로서 {@code Arrival}의 생명 주기에 종속됩니다.
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
    private ArrivalStatus status;

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
        this.status = ArrivalStatus.ARRIVED; // 초기 상태는 도착 완료로 설정
    }

    public List<ArrivalItem> getArrivalItems() {
        return Collections.unmodifiableList(arrivalItems);
    }

    private void addArrivalItem(ArrivalItem arrivalItem) {
        arrivalItems.add(arrivalItem);
        arrivalItem.assignArrival(this);
    }
}
