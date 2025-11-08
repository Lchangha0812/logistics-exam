package io.lchangha.logisticsexam.domain.masterdata.partner.exception;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.masterdata.partner.Partner;

/**
 * {@code InvalidPartnerException}은 {@link Partner} 의
 * 유효성 검증 규칙을 위반했을 때 발생하는 예외입니다.
 * 예를 들어, 파트너명, 주소, 연락처 정보 등 필수 필드가 누락되었거나,
 * 유효하지 않은 값이 설정되었을 때 이 예외가 발생합니다.
 */
public class InvalidPartnerException extends DomainException {
    public InvalidPartnerException(String message) {
        super(message);
    }
}
