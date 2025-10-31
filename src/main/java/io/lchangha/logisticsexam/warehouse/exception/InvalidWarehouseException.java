package io.lchangha.logisticsexam.warehouse.exception;

import io.lchangha.logisticsexam.common.exception.DomainException;

/**
 * {@code InvalidWarehouseException}은 {@link io.lchangha.logisticsexam.warehouse.domain.Warehouse} 애그리거트의
 * 유효성 검증 규칙을 위반했을 때 발생하는 예외입니다.
 * 예를 들어, 창고명, 주소, 유형 등 필수 필드가 누락되었거나,
 * 유효하지 않은 값이 설정되었을 때 이 예외가 발생합니다.
 */
public class InvalidWarehouseException extends DomainException {
    public InvalidWarehouseException(String message) {
        super(message);
    }
}
