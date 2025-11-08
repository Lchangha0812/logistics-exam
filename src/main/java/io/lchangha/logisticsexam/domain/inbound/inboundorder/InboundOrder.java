package io.lchangha.logisticsexam.domain.inbound.inboundorder;

import io.lchangha.logisticsexam.domain.DomainValidator;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.contract.InboundOrderIdGenerator;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.params.RegisterItemParam;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.params.RegistrationParam;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.*;
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
 * {@code InboundOrder} 는 들어올 상품에 대한 계획된 주문 정보를 나타내며 입하 이전 사전 준비를 포함합니다.
 * 이는 외부 시스템으로부터 수신되거나 수동으로 생성될 수 있으며, 입고 프로세스의 시작점 역할을 합니다.
 *
 * <ul>
 *     <li>**주문 식별:** {@link InboundOrderId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**참조 코드:** {@link ReferenceCode}를 통해 입고 유형별(구매, 반품, 이동 등) 참조 코드를 다형적으로 관리합니다.</li>
 *     <li>**주문 정보:** 주문 제목({@link InboundOrderTitle}), 설명({@link InboundOrderDescription}), 요청일({@code orderRequestedAt}), 예상 도착일({@code expectedArrivalAt}) 등 주문의 상세 정보를 포함합니다.</li>
 *     <li>**주문 품목 관리:** {@link InboundOrderItem} 목록을 관리하여 어떤 상품이 얼마나 입고될 예정인지 상세히 정의합니다.</li>
 *     <li>**상태 관리:** {@link InboundOrderStatus}를 통해 주문의 현재 상태(예: 요청됨, 승인됨, 완료됨 등)를 추적합니다.</li>
 * </ul>
 *
 * {@code InboundOrder}는 입고 프로세스의 첫 번째 단계로, 이후 {@code Arrival} 및 {@code Receiving} 와 연동되어
 * 실제 상품의 입고 및 적치 과정을 관리합니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class InboundOrder {
    private InboundOrderId id;
    private final ReferenceCode referenceCode;
    private final InboundOrderTitle title;
    private final InboundOrderDescription description;
    private final LocalDateTime orderRequestedAt;
    private final LocalDateTime expectedArrivalAt;
    private InboundOrderStatus status;
    private final List<InboundOrderItem> inboundOrderItems = new ArrayList<>();

    @Builder(access = AccessLevel.PACKAGE)
    private InboundOrder(
            InboundOrderId id,
            ReferenceCode referenceCode,
            InboundOrderTitle title,
            InboundOrderDescription description,
            LocalDateTime orderRequestedAt,
            LocalDateTime expectedArrivalAt,
            List<InboundOrderItem> inboundOrderItems,
            InboundOrderStatus inboundOrderStatus) {
        this.id = id;
        this.referenceCode = DomainValidator.requireNonNull(referenceCode, () -> new InvalidInboundOrderException("참조 코드는 필수입니다."));
        this.title = DomainValidator.requireNonNull(title, () -> new InvalidInboundOrderException("입고 주문 제목은 필수입니다."));
        this.description = DomainValidator.requireNonNull(description, () -> new InvalidInboundOrderException("입고 주문 설명은 필수입니다."));
        this.orderRequestedAt = DomainValidator.requireNonNull(orderRequestedAt, () -> new InvalidInboundOrderException("입고 요청일은 필수입니다."));
        this.expectedArrivalAt = DomainValidator.requireNonNull(expectedArrivalAt, () -> new InvalidInboundOrderException("예상 도착일은 필수입니다."));
        DomainValidator.requireNonEmpty(inboundOrderItems, () -> new InvalidInboundOrderException("입고 주문 상품은 필수입니다."));
        inboundOrderItems.forEach(this::addInboundOrderItem); //양방향 참조를 위해
        this.status = inboundOrderStatus;
    }

    public static InboundOrder register(RegistrationParam info, InboundOrderIdGenerator idGenerator) {
        List<InboundOrderItem> newItems = info.items().stream()
                .map(itemParam -> InboundOrderItem.builder()
                        .id(idGenerator.generateItemId())
                        .productId(itemParam.productId())
                        .orderQuantity(itemParam.quantity())
                        .unitPrice(itemParam.unitPrice())
                        .lotNumber(itemParam.lotNumber())
                        .build())
                .toList();

        return InboundOrder.builder()
                .id(idGenerator.generateId())
                .referenceCode(info.referenceCode())
                .title(info.title())
                .description(info.description())
                .orderRequestedAt(info.orderRequestedAt())
                .expectedArrivalAt(info.expectedArrivalAt())
                .inboundOrderItems(newItems)
                .inboundOrderStatus(InboundOrderStatus.REQUESTED)
                .build();
    }

    public List<InboundOrderItem> getInboundOrderItems() {
        return Collections.unmodifiableList(inboundOrderItems);
    }

    private void addInboundOrderItem(InboundOrderItem item) {
        this.inboundOrderItems.add(item);
        item.assignInboundOrder(this);
    }

    /**
     * 입고 주문을 확정합니다.
     * @throws InvalidInboundOrderException 이미 확정되었거나 취소된 주문일 경우   */
    public void confirm() {
        if (this.status != InboundOrderStatus.REQUESTED) {
            throw new InvalidInboundOrderException("요청 상태의 주문만 확정할 수 있습니다.");
        }
        this.status = InboundOrderStatus.CONFIRMED;
    }

    /**
     * 입고 주문을 취소합니다
     * @throws InvalidInboundOrderException 이미 확정되어 입고가 진행중인 주문일 경우
     * */
    public void cancel() {
        if (this.status == InboundOrderStatus.CONFIRMED) {
            // TODO: 이미 입고가 진행중(Arrival 생성)이거나 기한이 지나면 취소할 수 없거나 등 복잡한 정책이 추가될 수 있음
            throw new InvalidInboundOrderException("확정된 주문은 취소할 수 없습니다. 별도의 반품/취소 프로세스를 진행해야 합니다.");
        }
        this.status = InboundOrderStatus.CANCELLED;
    }

    // TODO: 사전 준비 유스케이스 메서드들

}
