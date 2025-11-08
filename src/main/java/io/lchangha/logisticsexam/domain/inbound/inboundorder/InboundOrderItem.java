package io.lchangha.logisticsexam.domain.inbound.inboundorder;

import io.lchangha.logisticsexam.domain.DomainValidator;
import io.lchangha.logisticsexam.domain.LotNumber;
import io.lchangha.logisticsexam.domain.Quantity;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderItemId;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * {@code InboundOrderItem}은 {@link InboundOrder}의 구성 요소로,
 * 특정 입고 주문에 포함된 개별 상품의 정보를 나타냅니다.
 * {@code InboundOrder} 의 생명 주기에 종속되며, {@code InboundOrder} 없이는 존재할 수 없습니다.
 *
 * 이 엔티티는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**입고 주문 상품 식별:** {@link InboundOrderItemId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**상품 참조:** {@link ProductId}를 통해 어떤 상품이 입고될 예정인지 참조합니다.</li>
 *     <li>**예정 수량 및 단가:** 입고될 예정인 상품의 수량과 단가를 정의합니다.</li>
 *     <li>**입고 주문:** {@link InboundOrder}에 속하며, {@code InboundOrder}와의 관계를 관리합니다.</li>
 * </ul>
 *
 * {@code InboundOrderItem}은 {@code InboundOrder} 의 불변성을 유지하는 데 기여하며,
 * {@code InboundOrder} 의 일관성 경계 내에서 관리됩니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class InboundOrderItem {
    private InboundOrderItemId id;
    private final ProductId productId;
    private final Quantity orderQuantity;
    private final Long unitPrice;
    private final LotNumber lotNumber;
    private InboundOrder inboundOrder; // 양방향 참조용도

    @Builder(access = AccessLevel.PACKAGE)
    private InboundOrderItem(
            InboundOrderItemId id,
            ProductId productId,
            Quantity orderQuantity,
            Long unitPrice,
            LotNumber lotNumber) {
        this.id = id;
        this.productId = DomainValidator.requireNonNull(productId, () -> new InvalidInboundOrderException("상품 ID는 필수입니다."));
        this.orderQuantity = DomainValidator.requireNonNull(orderQuantity, () -> new InvalidInboundOrderException("주문 수량은 필수입니다."));
        DomainValidator.isTrue(orderQuantity.amount() > 0, () -> new InvalidInboundOrderException("주문 수량은 0보다 커야 합니다."));
        this.unitPrice = DomainValidator.requireNonNull(unitPrice, () -> new InvalidInboundOrderException("단가는 필수입니다."));
        DomainValidator.isTrue(unitPrice > 0, () -> new InvalidInboundOrderException("단가는 0보다 커야 합니다."));
        this.lotNumber = DomainValidator.requireNonNull(lotNumber, () -> new InvalidInboundOrderException("로트 번호는 필수입니다."));
    }

    // 양방향 참조를 위한 사실상의 세터
    void assignInboundOrder(InboundOrder inboundOrder) {
        this.inboundOrder = inboundOrder;
    }
}
