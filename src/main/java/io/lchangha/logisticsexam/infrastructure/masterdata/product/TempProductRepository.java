package io.lchangha.logisticsexam.infrastructure.masterdata.product;

import io.lchangha.logisticsexam.domain.masterdata.product.Product;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TempProductRepository implements ProductRepository {
    private final Map<ProductId, Product> products = new HashMap<>();

    // The interface does not define a save method, so it's not implemented here.

    @Override
    public Optional<Product> findById(ProductId productId) {
        return Optional.ofNullable(products.get(productId));
    }
}
