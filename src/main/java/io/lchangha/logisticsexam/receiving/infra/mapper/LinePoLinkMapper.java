package io.lchangha.logisticsexam.receiving.infra.mapper;

import io.lchangha.logisticsexam.receiving.domain.vo.LinePoLink;
import io.lchangha.logisticsexam.receiving.infra.repo.entity.LinePoLinkEntity;
import org.springframework.stereotype.Component;

@Component
public class LinePoLinkMapper {

    public LinePoLink toDomain(LinePoLinkEntity entity) {
        if (entity == null) {
            return null;
        }
        return new LinePoLink(entity.getPoLineId(), entity.getQuantity());
    }

    public LinePoLinkEntity toEntity(LinePoLink domain) {
        if (domain == null) {
            return null;
        }
        return LinePoLinkEntity.builder()
                .poLineId(domain.poLineId())
                .quantity(domain.quantity())
                .build();
    }
}
