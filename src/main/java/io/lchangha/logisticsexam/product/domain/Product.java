package io.lchangha.logisticsexam.product.domain;

import io.lchangha.logisticsexam.common.util.DomainValidator;
import io.lchangha.logisticsexam.id.PartnerId;
import io.lchangha.logisticsexam.id.ProductId;
import io.lchangha.logisticsexam.product.exception.InvalidProductException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Objects;

/**
 * {@code Product} 애그리거트 루트는 WMS(창고 관리 시스템)에서 관리되는 모든 상품의 마스터 데이터를 정의합니다.
 * 상품은 창고에 입고, 보관, 출고되는 모든 물리적 품목의 고유한 특성과 정책을 나타냅니다.
 *
 * 이 애그리거트는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**상품 식별:** {@link ProductId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**기본 정보 정의:** 상품명, 상품 코드, 설명 등 기본적인 식별 정보를 제공합니다.</li>
 *     <li>**물리적 특성:** 상품의 크기({@link ProductSize})와 무게({@link ProductWeight})를 정의하여 보관 공간 계산, 운송 계획 등에 활용됩니다.</li>
 *     <li>**거래 관계:** 공급업체({@link PartnerId supplierId}) 및 제조업체({@link PartnerId manufacturerId}) 정보를 통해 상품의 출처를 관리합니다.</li>
 *     <li>**단위 관리:** {@link ProductUnit}을 통해 상품의 기본 단위, 포장 단위 및 각 단위당 수량을 정의하여 재고 관리의 정확성을 높입니다.</li>
 *     <li>**분류 및 정책:** {@link ProductCategory}를 통해 상품을 분류하고, {@link StorageStrategy} (적치 전략), {@link PickingStrategy} (피킹 전략) 등 창고 운영에 필요한 정책을 정의합니다.</li>
 *     <li>**재고 관리 정책:** 유효기간 필요 여부({@code requiresExpirationDate}), 유효기간 일수({@code expirationPeriodDays}), 재주문 시점({@code reorderPoint}), 최대 재고 수량({@code maxStockLevel}) 등 재고 운영에 필요한 정책을 포함합니다.</li>
 * </ul>
 *
 * {@code Product}는 다른 애그리거트(예: {@code InboundItem})에서 {@link ProductId}를 통해 참조되며,
 * 상품의 핵심 속성 변경은 이 애그리거트 내에서만 이루어져 데이터 일관성을 보장합니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Product {
    private ProductId id;
    private final ProductName name;
    private final ProductCode code;
    private final ProductDescription description;
    private final ProductSize size;
    private final ProductWeight weight;
    private final PartnerId supplierId;
    private final PartnerId manufacturerId;
    private final ProductUnit productUnit;
    private final ProductCategory category;
    private final boolean requiresExpirationDate;
    private final Long expirationPeriodDays;
    private final StorageStrategy storageStrategy;
    private final PickingStrategy pickingStrategy;
    private final Long reorderPoint;
    private final Long maxStockLevel;

    @Builder(access = AccessLevel.PACKAGE)
    private Product(
            ProductId id,
            ProductName name,
            ProductCode code,
            ProductDescription description,
            ProductSize size,
            ProductWeight weight,
            PartnerId supplierId,
            PartnerId manufacturerId,
            ProductUnit productUnit,
            ProductCategory category,
            boolean requiresExpirationDate,
            Long expirationPeriodDays,
            StorageStrategy storageStrategy,
            PickingStrategy pickingStrategy,
            Long reorderPoint,
            Long maxStockLevel) {
        this.id = id;
        this.name = Objects.requireNonNull(name, "상품명은 필수입니다.");
        this.code = Objects.requireNonNull(code, "상품 코드는 필수입니다.");
        this.description = Objects.requireNonNull(description, "상품설명은 필수입니다.");
        this.size = Objects.requireNonNull(size, "상품 크기는 필수입니다.");
        this.weight = Objects.requireNonNull(weight, "상품 무게는 필수입니다.");
        this.supplierId = supplierId;
        this.manufacturerId = manufacturerId;
        this.productUnit = Objects.requireNonNull(productUnit, "상품 단위 정보는 필수입니다.");
        this.category = Objects.requireNonNull(category, "상품 카테고리는 필수입니다.");
        this.requiresExpirationDate = requiresExpirationDate;
        this.expirationPeriodDays = requiresExpirationDate ? Objects.requireNonNull(expirationPeriodDays, "유효기간이 필요한 상품은 유효기간 일수가 필수입니다.") : null;
        if (requiresExpirationDate && expirationPeriodDays <= 0) {
            throw new IllegalArgumentException("유효기간 일수는 0보다 커야 합니다.");
        }
        this.storageStrategy = Objects.requireNonNull(storageStrategy, "적치 전략은 필수입니다.");
        this.pickingStrategy = Objects.requireNonNull(pickingStrategy, "피킹 전략은 필수입니다.");
        this.reorderPoint = reorderPoint;
        if (reorderPoint != null && reorderPoint < 0) {
            throw new IllegalArgumentException("최소 재고 수량은 0보다 작을 수 없습니다.");
        }
        this.maxStockLevel = maxStockLevel;
        if (maxStockLevel != null && maxStockLevel < 0) {
            throw new IllegalArgumentException("최대 재고 수량은 0보다 작을 수 없습니다.");
        }
        if (reorderPoint != null && maxStockLevel != null && reorderPoint > maxStockLevel) {
            throw new IllegalArgumentException("최소 재고 수량은 최대 재고 수량보다 클 수 없습니다.");
        }
    }
}
