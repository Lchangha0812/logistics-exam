package io.lchangha.logisticsexam.purchasing.infra.repo;

import io.lchangha.logisticsexam.masterdata.item.domain.vo.Quantity;
import io.lchangha.logisticsexam.masterdata.item.domain.vo.Uom;
import io.lchangha.logisticsexam.masterdata.supplier.domain.vo.SupplierId;
import io.lchangha.logisticsexam.masterdata.vo.ItemId;
import io.lchangha.logisticsexam.purchasing.domain.PO;
import io.lchangha.logisticsexam.purchasing.domain.contract.PORepository;
import io.lchangha.logisticsexam.purchasing.domain.dto.POLineState;
import io.lchangha.logisticsexam.purchasing.domain.vo.OverReceivePolicy;
import io.lchangha.logisticsexam.purchasing.domain.vo.POId;
import io.lchangha.logisticsexam.purchasing.domain.vo.POLineId;
import io.lchangha.logisticsexam.purchasing.infra.entity.POEntity;
import io.lchangha.logisticsexam.purchasing.infra.entity.POLineEntity;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * PORepository의 JPA 기반 구현체.
 */
@Repository
public class PORepositoryImpl implements PORepository {

    private final PurchaseOrderJpaRepository purchaseOrderJpaRepository;

    public PORepositoryImpl(PurchaseOrderJpaRepository purchaseOrderJpaRepository) {
        this.purchaseOrderJpaRepository = purchaseOrderJpaRepository;
    }

    @Override
    public void save(PO po) {
        purchaseOrderJpaRepository.save(toEntity(po));
    }

    @Override
    public Optional<PO> findById(POId poId) {
        return purchaseOrderJpaRepository.findById(poId.value())
                .map(this::toDomain);
    }

    @Override
    public void deleteById(POId poId) {
        purchaseOrderJpaRepository.deleteById(poId.value());
    }

    private PO toDomain(POEntity entity) {
        List<POLineState> lineStates = entity.getPurchaseOrderLines().stream()
                .map(this::toLineState)
                .toList();

        return PO.restore(
                new POId(entity.getId()),
                new SupplierId(entity.getSupplierId()),
                PO.Status.valueOf(entity.getStatus()),
                entity.getDescription(),
                entity.getOrderDate(),
                entity.getExpectedDate(),
                lineStates
        );
    }

    private POLineState toLineState(POLineEntity entity) {
        return new POLineState(
                new POLineId(entity.getId()),
                new ItemId(entity.getItemId()),
                Quantity.of(entity.getOrderedQuantity(), Uom.of(entity.getUom())),
                entity.getPrice(),
                entity.isAllowOverReceive() ? OverReceivePolicy.allowedTo(entity.getMaxOverRatio()) : OverReceivePolicy.notAllowed(),
                Quantity.of(entity.getReceivedQuantity(), Uom.of(entity.getUom()))
        );
    }

    private POEntity toEntity(PO po) {
        POEntity entity = POEntity.builder()
                .id(po.getId().value())
                .supplierId(po.getSupplierId().value())
                .description(po.getDescription())
                .status(po.getStatus().name())
                .orderDate(po.getOrderDate())
                .expectedDate(po.getExpectedDeliveryCompleteDate())
                .build();

        po.getLines().forEach(line -> {
            POLineEntity lineEntity = POLineEntity.builder()
                    .id(line.getId().value())
                    .itemId(line.getItemId().value())
                    .orderedQuantity(line.getTargetQty().value())
                    .uom(line.getTargetQty().uom().symbol())
                    .receivedQuantity(line.getReceivedQty().value())
                    .price(line.getPrice())
                    .allowOverReceive(line.getOverReceivePolicy().isAllowed())
                    .maxOverRatio(line.getOverReceivePolicy().getMaxOverRatio())
                    .build();
            entity.getPurchaseOrderLines().add(lineEntity);
        });

        return entity;
    }
}
