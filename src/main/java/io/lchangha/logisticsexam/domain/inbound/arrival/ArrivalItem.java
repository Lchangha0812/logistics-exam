package io.lchangha.logisticsexam.domain.inbound.arrival;

import io.lchangha.logisticsexam.domain.DomainValidator;
import io.lchangha.logisticsexam.domain.LotNumber;
import io.lchangha.logisticsexam.domain.Quantity;
import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalItemId;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ArrivalItem}은 {@link Arrival} 의 일부로,
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
    private final LotNumber lotNumber; // 로트 번호 추가

    // 클레임 관련 정보 (선택 사항)
    private final ClaimType claimType;
    private final String claimReason;
    private final List<String> evidencePhotoKeys;

    @Builder(access = AccessLevel.PACKAGE)
    private ArrivalItem(
            ArrivalItemId id,
            ProductId productId,
            Quantity arrivedQuantity,
            LotNumber lotNumber,
            ClaimType claimType,
            String claimReason,
            List<String> evidencePhotoKeys) {
        this.id = id;
        this.productId = DomainValidator.requireNonNull(productId, () -> new InvalidArrivalException("상품 ID는 필수입니다."));
        this.arrivedQuantity = DomainValidator.requireNonNull(arrivedQuantity, () -> new InvalidArrivalException("도착 수량은 필수입니다."));
        DomainValidator.isTrue(arrivedQuantity.amount() > 0, () -> new InvalidArrivalException("도착 수량은 0보다 커야 합니다."));
        this.lotNumber = DomainValidator.requireNonNull(lotNumber, () -> new InvalidArrivalException("로트 번호는 필수입니다."));
        this.claimType = claimType;
        this.claimReason = claimReason;
        this.evidencePhotoKeys = evidencePhotoKeys == null ? new ArrayList<>() : evidencePhotoKeys;
    }
}
