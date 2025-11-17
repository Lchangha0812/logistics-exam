package io.lchangha.logisticsexam.receiving.infra.mapper;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceiptLine;
import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptLineId;
import io.lchangha.logisticsexam.receiving.domain.vo.LotInfo;
import io.lchangha.logisticsexam.receiving.domain.vo.ReceiptLineDiscrepancy;
import io.lchangha.logisticsexam.receiving.infra.repo.entity.GoodsReceiptLineEntity;
import io.lchangha.logisticsexam.receiving.infra.repo.entity.LinePoLinkEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GoodsReceiptLineMapper {

    private final LinePoLinkMapper linePoLinkMapper;

    public GoodsReceiptLineMapper(LinePoLinkMapper linePoLinkMapper) {
        this.linePoLinkMapper = linePoLinkMapper;
    }

    public GoodsReceiptLine toDomain(GoodsReceiptLineEntity entity) {
        if (entity == null) {
            return null;
        }
        return GoodsReceiptLine.reconstitute(
                new GoodsReceiptLineId(entity.getId()),
                entity.getItemId(),
                entity.getUom(),
                entity.getQuantity(),
                entity.getToLocationId(),
                new LotInfo(entity.getLotCode(), entity.getExpiryDate()),
                entity.isRequiresExpiry(),
                entity.isFreeLine(),
                entity.getOrderedQty(),
                Optional.ofNullable(entity.getDiscrepancyCode())
                        .map(code -> new ReceiptLineDiscrepancy(code, entity.getDiscrepancyNote()))
                        .orElse(null),
                Optional.ofNullable(entity.getPoLinks())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(linePoLinkMapper::toDomain)
                        .collect(Collectors.toList())
        );
    }

    public GoodsReceiptLineEntity toEntity(GoodsReceiptLine domain) {
        if (domain == null) {
            return null;
        }
        GoodsReceiptLineEntity entity = GoodsReceiptLineEntity.builder()
                .id(domain.getId().value())
                .itemId(domain.getItemId())
                .uom(domain.getUom())
                .quantity(domain.getQuantity())
                .toLocationId(domain.getToLocationId())
                .lotCode(Optional.ofNullable(domain.getLotInfo()).map(LotInfo::lotCode).orElse(null))
                .expiryDate(Optional.ofNullable(domain.getLotInfo()).map(LotInfo::expiryDate).orElse(null))
                .requiresExpiry(domain.isRequiresExpiry())
                .freeLine(domain.isFreeLine())
                .orderedQty(domain.getOrderedQty())
                .discrepancyCode(Optional.ofNullable(domain.getDiscrepancy()).map(ReceiptLineDiscrepancy::code).orElse(null))
                .discrepancyNote(Optional.ofNullable(domain.getDiscrepancy()).map(ReceiptLineDiscrepancy::note).orElse(null))
                .build();

        Optional.ofNullable(domain.getPoLinks())
                .orElse(Collections.emptyList())
                .stream()
                .map(link -> {
                    LinePoLinkEntity linkEntity = linePoLinkMapper.toEntity(link);
                    linkEntity.setGoodsReceiptLine(entity); // 양방향 관계 설정
                    return linkEntity;
                })
                .forEach(entity.getPoLinks()::add); // 엔티티의 링크 리스트에 추가

        return entity;
    }
}