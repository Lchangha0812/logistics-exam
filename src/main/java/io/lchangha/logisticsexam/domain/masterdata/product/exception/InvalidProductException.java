package io.lchangha.logisticsexam.domain.masterdata.product.exception;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.masterdata.product.Product;

/**
 * {@code InvalidProductException}은 {@link Product} 의
 * 유효성 검증 규칙을 위반했을 때 발생하는 예외입니다.
 * 예를 들어, 상품명, 상품 코드 등 필수 필드가 누락되었거나,
 * 유효하지 않은 값이 설정되었을 때 이 예외가 발생합니다.
 */
public class InvalidProductException extends DomainException {
    public InvalidProductException(String message) {
        super(message);
    }
}
