package io.lchangha.logisticsexam.product.domain;

import io.lchangha.logisticsexam.common.exception.DomainException;
import io.lchangha.logisticsexam.common.util.DomainValidator;

public record ProductSize(Long width, Long height, Long depth) {
    public ProductSize {
        DomainValidator.requireNonNull(width, () -> new DomainException("상품 너비는 필수입니다."));
        DomainValidator.isTrue(width > 0, () -> new DomainException("상품 너비는 0보다 커야 합니다."));

        DomainValidator.requireNonNull(height, () -> new DomainException("상품 높이는 필수입니다."));
        DomainValidator.isTrue(height > 0, () -> new DomainException("상품 높이는 0보다 커야 합니다."));

        DomainValidator.requireNonNull(depth, () -> new DomainException("상품 깊이는 필수입니다."));
        DomainValidator.isTrue(depth > 0, () -> new DomainException("상품 깊이는 0보다 커야 합니다."));
    }

    public static ProductSize of(Long width, Long height, Long depth) {
        return new ProductSize(width, height, depth);
    }

    public Long volume() {
        return width * height * depth;
    }
}
