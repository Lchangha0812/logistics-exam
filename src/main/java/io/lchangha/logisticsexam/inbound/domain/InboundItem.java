package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.id.InboundItemId;
import io.lchangha.logisticsexam.id.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class InboundItem {
    private InboundItemId id;
    private final ProductId productId;
    private final Long quantity;
    private final Long unitPrice;
    private Inbound inbound;

    @Builder(access = AccessLevel.PACKAGE)
    private InboundItem(
            InboundItemId id,
            ProductId productId,
            Long quantity,
            Long unitPrice) {
        validate(productId, quantity, unitPrice);
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    private void validate(ProductId productId, Long quantity, Long unitPrice) {
        Objects.requireNonNull(productId, "상품 ID는 필수입니다.");
        Objects.requireNonNull(quantity, "수량은 필수입니다.");
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 0보다 커야 합니다.");
        }
        Objects.requireNonNull(unitPrice, "단가는 필수입니다.");
        if (unitPrice <= 0) {
            throw new IllegalArgumentException("단가는 0보다 커야 합니다.");
        }
    }

    public void assignInbound(Inbound inbound) {
        Objects.requireNonNull(inbound, "입고는 필수입니다.");
        this.inbound = inbound;
    }
}
