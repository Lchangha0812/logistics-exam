package io.lchangha.logisticsexam.domain;

public record LotNumber(String value) {
    public LotNumber {
        DomainValidator.requireNonBlank(value, () -> new IllegalArgumentException("로트 번호는 필수입니다."));
    }
}
