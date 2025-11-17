package io.lchangha.logisticsexam.receiving.infra.repo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "po_tolerance_policies")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PoTolerancePolicyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "over_percentage", nullable = false)
    private BigDecimal overPercentage;

    @Column(name = "under_percentage", nullable = false)
    private BigDecimal underPercentage;

    @Column(name = "allow_extra_items", nullable = false)
    private boolean allowExtraItems;

    @Builder
    public PoTolerancePolicyEntity(Long id, BigDecimal overPercentage, BigDecimal underPercentage, boolean allowExtraItems) {
        this.id = id;
        this.overPercentage = overPercentage;
        this.underPercentage = underPercentage;
        this.allowExtraItems = allowExtraItems;
    }
}
