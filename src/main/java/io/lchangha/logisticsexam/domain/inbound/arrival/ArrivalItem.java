package io.lchangha.logisticsexam.domain.inbound.arrival;

import io.lchangha.logisticsexam.domain.DomainValidator;
import io.lchangha.logisticsexam.domain.Quantity;
import io.lchangha.logisticsexam.domain.inbound.exception.InvalidArrivalException;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * {@code ArrivalItem}은 {@link Arrival} 애그리거트의 일부로,
 * 특정 입하 이벤트에서 물리적으로 도착했다고 '관찰된' 상품과 그 수량을 나타냅니다.
 * 이 엔티티는 검품(Inspection)과 분리되어, 순수하게 도착 사실만을 기록합니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class ArrivalItem {
    private ArrivalItemId id;
    private final ProductId productId;
    private final Quantity arrivedQuantity; // 실 도착 재고 (관찰된 수량)

    @Builder(access = AccessLevel.PACKAGE)
    private ArrivalItem(
            ArrivalItemId id,
            ProductId productId,
            Quantity arrivedQuantity) {
        this.id = id;
        this.productId = DomainValidator.requireNonNull(productId, () -> new InvalidArrivalException("상품 ID는 필수입니다."));
        this.arrivedQuantity = DomainValidator.requireNonNull(arrivedQuantity, () -> new InvalidArrivalException("도착 수량은 필수입니다."));
        DomainValidator.isTrue(arrivedQuantity.amount() > 0, () -> new InvalidArrivalException("도착 수량은 0보다 커야 합니다."));
    }
}
