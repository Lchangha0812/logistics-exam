package io.lchangha.logisticsexam.domain.masterdata.location.exception;

import io.lchangha.logisticsexam.domain.DomainException;
import io.lchangha.logisticsexam.domain.masterdata.location.Location;

/**
 * {@code InvalidLocationException}은 {@link Location} 애그리거트의
 * 유효성 검증 규칙을 위반했을 때 발생하는 예외입니다.
 * 예를 들어, 창고 ID, 로케이션 코드, 유형 등 필수 필드가 누락되었거나,
 * 유효하지 않은 값이 설정되었을 때 이 예외가 발생합니다.
 */
public class InvalidLocationException extends DomainException {
    public InvalidLocationException(String message) {
        super(message);
    }
}
