package io.lchangha.logisticsexam.domain.inbound.record;

import io.lchangha.logisticsexam.domain.Quantity;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
public class InboundRecordItem {
    private final ProductId productId;
    private final Quantity orderedQuantity;
    private Quantity receivedQuantity;

    @Builder(access = AccessLevel.PACKAGE)
    private InboundRecordItem(ProductId productId, Quantity orderedQuantity) {
        this.productId = productId;
        this.orderedQuantity = orderedQuantity;
        this.receivedQuantity = Quantity.zero(orderedQuantity.unit());
    }

    void receive(Quantity quantity) {
        if (!this.orderedQuantity.isSameUnit(quantity)) {
            throw new IllegalArgumentException("단위가 다른 수량을 입고할 수 없습니다.");
        }
        this.receivedQuantity = this.receivedQuantity.add(quantity);
    }

    boolean isFulfilled() {
        return receivedQuantity.isGreaterThanOrEqual(orderedQuantity);
    }
}
