package io.lchangha.logisticsexam.domain.inbound.inboundorder;

import io.lchangha.logisticsexam.domain.DomainValidator;
import io.lchangha.logisticsexam.domain.inbound.exception.InvalidInboundOrderException;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.params.RegistrationInfo;
import io.lchangha.logisticsexam.domain.inbound.record.InboundRecord;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * {@code InboundOrder} 애그리거트 루트는 WMS(창고 관리 시스템)로 들어올 상품에 대한 계획된 주문 정보를 나타냅니다.
 * 이는 외부 시스템(예: ERP)으로부터 수신되거나 수동으로 생성될 수 있으며, 입고 프로세스의 시작점 역할을 합니다.
 *
 * 이 애그리거트는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**주문 식별:** {@link InboundOrderId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**참조 코드:** {@link ReferenceCode}를 통해 입고 유형별(구매, 반품, 이동 등) 참조 코드를 다형적으로 관리합니다.</li>
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

    public static InboundOrder register(InboundOrderId inboundOrderId, List<InboundOrderItemId> itemIds, RegistrationInfo info) {
        DomainValidator.isTrue(itemIds.size() == info.items().size(),
                () -> new InvalidInboundOrderException("입고 품목 ID의 개수가 품목 정보의 개수와 일치하지 않습니다."));

        List<InboundOrderItem> newItems = new ArrayList<>();
        for (int i = 0; i < itemIds.size(); i++) {
            RegistrationInfo.RegisterItem currentRegisterItem = info.items().get(i);
            InboundOrderItemId currentItemId = itemIds.get(i);

            InboundOrderItem newItem = InboundOrderItem.builder()
                    .id(currentItemId)
                    .productId(currentRegisterItem.productId())
                    .orderQuantity(currentRegisterItem.quantity())
                    .unitPrice(currentRegisterItem.unitPrice())
                    .build();
            newItems.add(newItem);
        }

        return InboundOrder.builder()
                .id(inboundOrderId)
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

    // 사전 준비 유스케이스 메서드들

    public void planPutawayLocations() {
        // TODO: [사전준비] 최적 보관 위치 선정 (Put-away Planning) 유스케이스 구현 필요.
    }

    public void planLaborAndEquipment() {
        // TODO: [사전준비] 작업자 및 장비 배치 (Labor Planning) 유스케이스 구현 필요.
    }

    public void identifyCrossDockingOpportunities() {
        // TODO: [사전준비] 크로스도킹 기회 식별 (Cross-Docking) 유스케이스 구현 필요.
    }

    /**
     * 모든 입고 기록을 바탕으로 이 주문을 최종 마감합니다.
     *
     * @param records 이 주문과 관련된 모든 InboundRecord 목록
     */
    public void close(List<InboundRecord> records) {
        if (this.status != InboundOrderStatus.CONFIRMED) {
            throw new InvalidInboundOrderException("확정된 주문만 마감할 수 있습니다.");
        }
        //TODO: 추가 구현 필요
    }
}
