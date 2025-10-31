package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.common.util.DomainValidator;
import io.lchangha.logisticsexam.id.ArrivalItemId;
import io.lchangha.logisticsexam.id.ProductId;
import io.lchangha.logisticsexam.inbound.exception.InvalidArrivalException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * {@code ArrivalItem}은 {@link Arrival} 애그리거트의 일부로, 특정 입하에 포함된 개별 상품 품목을 나타냅니다.
 * 이는 {@code Arrival}의 일관성 경계 내에 속하는 엔티티입니다.
 *
 * 이 엔티티는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**품목 식별:** {@link ArrivalItemId}를 통해 입하 내에서 고유하게 식별됩니다.</li>
 *     <li>**상품 참조:** {@link ProductId}를 통해 어떤 상품이 입하되었는지 참조합니다.</li>
 *     <li>**도착 수량:** {@code arrivedQuantity}를 통해 해당 상품이 얼마나 도착했는지 나타냅니다.</li>
 *     <li>**검수 결과:** {@code inspectionPassed}를 통해 해당 상품의 검수 통과 여부를 나타냅니다.</li>
 * </ul>
 *
 * {@code ArrivalItem}은 {@code Arrival} 애그리거트의 생명주기를 따르며,
 * {@code Arrival} 없이는 독립적으로 존재할 수 없습니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class ArrivalItem {
    private ArrivalItemId id;
    private final ProductId productId;
    private final Long arrivedQuantity;
    private final boolean inspectionPassed;
    private Arrival arrival;

    @Builder(access = AccessLevel.PACKAGE)
    private ArrivalItem(
            ArrivalItemId id,
            ProductId productId,
            Long arrivedQuantity,
            boolean inspectionPassed) {
        this.id = id;
        this.productId = DomainValidator.requireNonNull(productId, () -> new InvalidArrivalException("상품 ID는 필수입니다."));
        this.arrivedQuantity = DomainValidator.requireNonNull(arrivedQuantity, () -> new InvalidArrivalException("도착 수량은 필수입니다."));
        DomainValidator.isTrue(arrivedQuantity > 0, () -> new InvalidArrivalException("도착 수량은 0보다 커야 합니다."));
        this.inspectionPassed = inspectionPassed;
    }

    // Arrival 애그리거트에서만 호출되어야 합니다.
    void assignArrival(Arrival arrival) {
        this.arrival = arrival;
    }
}
