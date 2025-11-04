package io.lchangha.logisticsexam.domain.inbound.inboundorder;

import io.lchangha.logisticsexam.domain.DomainValidator;

/**
 * 창고 간 이동 지시서 번호를 나타내는 값 객체입니다.
 */
public record TransferOrderCode(String value) implements ReferenceCode {
    public TransferOrderCode {
        DomainValidator.requireNonBlank(value, () -> new IllegalArgumentException("이동 지시서 코드는 필수입니다."));
    }
}
