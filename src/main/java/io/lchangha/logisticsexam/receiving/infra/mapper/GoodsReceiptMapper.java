package io.lchangha.logisticsexam.receiving.infra.mapper;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceipt;
import io.lchangha.logisticsexam.receiving.domain.PoTolerancePolicy;
import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptNumber;
import io.lchangha.logisticsexam.shared.domain.AuditInfo;
import io.lchangha.logisticsexam.receiving.infra.repo.entity.GoodsReceiptEntity;
import io.lchangha.logisticsexam.receiving.infra.repo.entity.GoodsReceiptLineEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class GoodsReceiptMapper {

    private final GoodsReceiptLineMapper goodsReceiptLineMapper;

    public GoodsReceiptMapper(GoodsReceiptLineMapper goodsReceiptLineMapper) {
        this.goodsReceiptLineMapper = goodsReceiptLineMapper;
    }

    public GoodsReceipt toDomain(GoodsReceiptEntity entity, PoTolerancePolicy poTolerancePolicy) {
        if (entity == null) {
            return null;
        }
        return GoodsReceipt.reconstitute(
                entity.getId(),
                new GoodsReceiptNumber(entity.getGrnNumber()),
                entity.getType(),
                entity.getStatus(),
                entity.getSupplierId(),
                entity.getPoId(),
                new AuditInfo(
                        entity.getCreatedAt(), entity.getCreatedBy(),
                        entity.getLastModifiedAt(), entity.getLastModifiedBy()
                ),
                Optional.ofNullable(entity.getLines())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(goodsReceiptLineMapper::toDomain)
                        .collect(Collectors.toList()),
                poTolerancePolicy,
                entity.getReceivedAt()
        );
    }

    public GoodsReceiptEntity toEntity(GoodsReceipt domain) {
        if (domain == null) {
            return null;
        }
        GoodsReceiptEntity entity = GoodsReceiptEntity.builder()
                .id(domain.getId())
                .grnNumber(domain.getGrnNumber().value())
                .type(domain.getType())
                .status(domain.getStatus())
                .supplierId(domain.getSupplierId())
                .poId(domain.getPoId())
                .receivedAt(domain.getReceivedAt())
                .createdAt(domain.getAuditInfo().createdAt())
                .createdBy(domain.getAuditInfo().createdBy())
                .lastModifiedAt(domain.getAuditInfo().lastModifiedAt())
                .lastModifiedBy(domain.getAuditInfo().lastModifiedBy())
                .poTolerancePolicyId(Optional.ofNullable(domain.getTolerancePolicy()).map(PoTolerancePolicy::getId).orElse(null))
                .build();

        Optional.ofNullable(domain.getLines())
                .orElse(Collections.emptyList())
                .stream()
                .map(line -> {
                    GoodsReceiptLineEntity lineEntity = goodsReceiptLineMapper.toEntity(line);
                    lineEntity.setGoodsReceipt(entity); // 양방향 관계 설정
                    return lineEntity;
                })
                .forEach(entity.getLines()::add); // 엔티티의 라인 리스트에 추가

        return entity;
    }
}