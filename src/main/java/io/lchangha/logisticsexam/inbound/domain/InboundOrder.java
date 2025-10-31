package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.common.util.DomainValidator;
import io.lchangha.logisticsexam.id.InboundOrderId;
import io.lchangha.logisticsexam.inbound.exception.InvalidInboundOrderException;
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
 * {@code InboundOrder} 애그리거트 루트는 WMS(창고 관리 시스템)로 들어올 상품에 대한 계획된 주문 정보를 나타냅니다.
 * 이는 외부 시스템(예: ERP)으로부터 수신되거나 수동으로 생성될 수 있으며, 입고 프로세스의 시작점 역할을 합니다.
 *
 * 이 애그리거트는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**주문 식별:** {@link InboundOrderId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**외부 시스템 연동:** {@link ErpOrderNumber}를 통해 외부 ERP 시스템의 주문 번호와 연동됩니다.</li>
 *     <li>**주문 정보:** 주문 제목({@link InboundOrderTitle}), 설명({@link InboundOrderDescription}), 요청일({@code orderRequestedAt}), 예상 도착일({@code expectedArrivalAt}) 등 주문의 상세 정보를 포함합니다.</li>
 *     <li>**주문 품목 관리:** {@link InboundOrderItem} 목록을 관리하여 어떤 상품이 얼마나 입고될 예정인지 상세히 정의합니다.</li>
 *     <li>**상태 관리:** {@link InboundOrderStatus}를 통해 주문의 현재 상태(예: 요청됨, 승인됨, 완료됨 등)를 추적합니다.</li>
 * </ul>
 *
 * {@code InboundOrder}는 입고 프로세스의 첫 번째 단계로, 이후 {@code Arrival} 및 {@code Receiving} 애그리거트와 연동되어
 * 실제 상품의 입고 및 적치 과정을 관리합니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class InboundOrder {
    private InboundOrderId id;
    private final ErpOrderNumber erpOrderNumber;
    private final InboundOrderTitle title;
    private final InboundOrderDescription description;
    private final LocalDateTime orderRequestedAt;
    private final LocalDateTime expectedArrivalAt;
    private InboundOrderStatus status;
    private final List<InboundOrderItem> inboundOrderItems = new ArrayList<>();

    @Builder(access = AccessLevel.PACKAGE)
    private InboundOrder(
            InboundOrderId id,
            ErpOrderNumber erpOrderNumber,
            InboundOrderTitle title,
            InboundOrderDescription description,
            LocalDateTime orderRequestedAt,
            LocalDateTime expectedArrivalAt,
            List<InboundOrderItem> inboundOrderItems) {
        this.id = id;
        this.erpOrderNumber = DomainValidator.requireNonNull(erpOrderNumber, () -> new InvalidInboundOrderException("ERP 주문 번호는 필수입니다."));
        this.title = DomainValidator.requireNonNull(title, () -> new InvalidInboundOrderException("입고 주문 제목은 필수입니다."));
        this.description = DomainValidator.requireNonNull(description, () -> new InvalidInboundOrderException("입고 주문 설명은 필수입니다."));
        this.orderRequestedAt = DomainValidator.requireNonNull(orderRequestedAt, () -> new InvalidInboundOrderException("입고 요청일은 필수입니다."));
        this.expectedArrivalAt = DomainValidator.requireNonNull(expectedArrivalAt, () -> new InvalidInboundOrderException("예상 도착일은 필수입니다."));
        DomainValidator.requireNonEmpty(inboundOrderItems, () -> new InvalidInboundOrderException("입고 주문 상품은 필수입니다."));
        inboundOrderItems.forEach(this::addInboundOrderItem);
        this.status = InboundOrderStatus.REQUESTED;
    }

    public List<InboundOrderItem> getInboundOrderItems() {
        return Collections.unmodifiableList(inboundOrderItems);
    }

    private void addInboundOrderItem(InboundOrderItem item) {
        this.inboundOrderItems.add(item);
    }
}
