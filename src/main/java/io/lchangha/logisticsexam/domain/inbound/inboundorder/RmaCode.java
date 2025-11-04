package io.lchangha.logisticsexam.domain.inbound.inboundorder;

import io.lchangha.logisticsexam.domain.DomainValidator;

/**
 * 반품 승인 번호(RMA)를 나타내는 값 객체입니다.
 */
public record RmaCode(String value) implements ReferenceCode {
    public RmaCode {
        DomainValidator.requireNonBlank(value, () -> new IllegalArgumentException("RMA 코드는 필수입니다."));
    }
}
