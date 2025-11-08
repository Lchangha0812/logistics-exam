package io.lchangha.logisticsexam.domain.inbound.inboundorder.vo;

import io.lchangha.logisticsexam.domain.DomainValidator;

/**
 * 구매 주문에 대한 참조 번호를 나타내는 값 객체입니다.
 */
public record PurchaseOrderCode(String value) implements ReferenceCode {
    public PurchaseOrderCode {
        DomainValidator.requireNonBlank(value, () -> new IllegalArgumentException("구매 주문 코드는 필수입니다."));
    }

    public static PurchaseOrderCode of(String value) {
        return new PurchaseOrderCode(value);
    }
}
