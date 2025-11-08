package io.lchangha.logisticsexam.domain.masterdata.product;

import java.util.Optional;

public interface ProductRepository {
    Optional<Product> findById(ProductId productId);
}
