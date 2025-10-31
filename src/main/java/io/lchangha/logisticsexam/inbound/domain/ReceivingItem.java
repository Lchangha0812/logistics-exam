package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.common.util.DomainValidator;
import io.lchangha.logisticsexam.id.LocationId;
import io.lchangha.logisticsexam.id.ProductId;
import io.lchangha.logisticsexam.id.ReceivingItemId;
import io.lchangha.logisticsexam.inbound.exception.InvalidReceivingException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * {@code ReceivingItem}은 {@link Receiving} 애그리거트의 일부로, 특정 입고에 포함된 개별 상품 품목을 나타냅니다.
 * 이는 {@code Receiving}의 일관성 경계 내에 속하는 엔티티입니다.
 *
 * 이 엔티티는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**품목 식별:** {@link ReceivingItemId}를 통해 입고 내에서 고유하게 식별됩니다.</li>
 *     <li>**상품 참조:** {@link ProductId}를 통해 어떤 상품이 입고되었는지 참조합니다.</li>
 *     <li>**입고 수량:** {@code receivedQuantity}를 통해 해당 상품이 얼마나 입고되었는지 나타냅니다.</li>
 *     <li>**로케이션 할당:** {@link LocationId}를 통해 입고된 상품이 어느 로케이션에 적치되었는지 기록합니다.</li>
 * </ul>
 *
 * {@code ReceivingItem}은 {@code Receiving} 애그리거트의 생명주기를 따르며,
 * {@code Receiving} 없이는 독립적으로 존재할 수 없습니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class ReceivingItem {
    private ReceivingItemId id;
    private final ProductId productId;
    private final Long receivedQuantity;
    private final LocationId locationId;
    private Receiving receiving;

    @Builder(access = AccessLevel.PACKAGE)
    private ReceivingItem(
            ReceivingItemId id,
            ProductId productId,
            Long receivedQuantity,
            LocationId locationId) {
        this.id = id;
        this.productId = DomainValidator.requireNonNull(productId, () -> new InvalidReceivingException("상품 ID는 필수입니다."));
        this.receivedQuantity = DomainValidator.requireNonNull(receivedQuantity, () -> new InvalidReceivingException("입고 수량은 필수입니다."));
        DomainValidator.isTrue(receivedQuantity > 0, () -> new InvalidReceivingException("입고 수량은 0보다 커야 합니다."));
        this.locationId = DomainValidator.requireNonNull(locationId, () -> new InvalidReceivingException("로케이션 ID는 필수입니다."));
    }

    // Receiving 애그리거트에서만 호출되어야 합니다.
    void assignReceiving(Receiving receiving) {
        this.receiving = receiving;
    }
}
