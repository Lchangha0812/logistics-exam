package io.lchangha.logisticsexam.common.exception;

/**
 * {@code DomainException}은 도메인 계층에서 발생하는 모든 비즈니스 규칙 위반 또는
 * 도메인 관련 오류를 나타내는 최상위 예외 클래스입니다.
 * 이 예외는 런타임 예외({@link RuntimeException})를 상속하여, 복구 불가능하거나
 * 애플리케이션 수준에서 처리해야 하는 도메인 오류를 표현하는 데 사용됩니다.
 *
 * <p>이 예외를 상속받아 각 도메인 애그리거트나 특정 비즈니스 로직에 특화된
 * 구체적인 예외 클래스를 정의할 수 있습니다. 예를 들어,
 * {@code InvalidProductException}, {@code InvalidInboundOrderException} 등이 있습니다.</p>
 */
public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
