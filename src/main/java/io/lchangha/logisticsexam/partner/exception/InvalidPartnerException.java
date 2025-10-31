package io.lchangha.logisticsexam.partner.exception;

import io.lchangha.logisticsexam.common.exception.DomainException;

/**
 * {@code InvalidPartnerException}은 {@link io.lchangha.logisticsexam.partner.domain.Partner} 애그리거트의
 * 유효성 검증 규칙을 위반했을 때 발생하는 예외입니다.
 * 예를 들어, 파트너명, 주소, 연락처 정보 등 필수 필드가 누락되었거나,
 * 유효하지 않은 값이 설정되었을 때 이 예외가 발생합니다.
 */
public class InvalidPartnerException extends DomainException {
    public InvalidPartnerException(String message) {
        super(message);
    }
}
