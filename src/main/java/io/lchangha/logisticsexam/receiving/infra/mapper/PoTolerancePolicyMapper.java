package io.lchangha.logisticsexam.receiving.infra.mapper;

import io.lchangha.logisticsexam.receiving.domain.PoTolerancePolicy;
import io.lchangha.logisticsexam.receiving.infra.repo.entity.PoTolerancePolicyEntity;
import org.springframework.stereotype.Component;

@Component
public class PoTolerancePolicyMapper {

    public PoTolerancePolicy toDomain(PoTolerancePolicyEntity entity) {
        if (entity == null) {
            return null;
        }
        return PoTolerancePolicy.reconstitute(
                entity.getId(),
                entity.getOverPercentage(),
                entity.getUnderPercentage(),
                entity.isAllowExtraItems()
        );
    }

    public PoTolerancePolicyEntity toEntity(PoTolerancePolicy domain) {
        if (domain == null) {
            return null;
        }
        return PoTolerancePolicyEntity.builder()
                .id(domain.getId())
                .overPercentage(domain.getOverPercentage())
                .underPercentage(domain.getUnderPercentage())
                .allowExtraItems(domain.isAllowExtraItems())
                .build();
    }
}
