package io.lchangha.logisticsexam.masterdata.infra.mapper;

import io.lchangha.logisticsexam.masterdata.domain.Item;
import io.lchangha.logisticsexam.masterdata.domain.vo.*;
import io.lchangha.logisticsexam.masterdata.infra.entity.ItemEntity;
import io.lchangha.logisticsexam.shared.domain.AuditInfo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Component
public class ItemEntityMapper {

    public Item toDomain(ItemEntity entity) {
        return Item.builder()
                .id(entity.getId())
                .name(entity.getName())
                .sku(new SKU(entity.getSkuCode()))
                .barcode(new Barcode(entity.getBarcode()))
                .baseUom(Uom.of(entity.getBaseUom()))
                .itemCategory(ItemCategory.valueOf(entity.getItemCategory().toUpperCase()))
                .temperatureZone(TemperatureZone.valueOf(entity.getTemperatureZone().toUpperCase()))
                .requiresExpiry(entity.isRequiresExpiry())
                .safetyStock(entity.getSafetyStock())
                .active(entity.isActive())
                .uomConversionProfile(mapUomConversionProfile(entity))
                .auditInfo(new AuditInfo(
                        entity.getCreatedAt(),
                        entity.getCreatedBy(),
                        entity.getLastModifiedAt(),
                        entity.getLastModifiedBy()
                ))
                .build();
    }

    public ItemEntity toEntity(Item domain) {
        return ItemEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .skuCode(domain.getSku().value())
                .barcode(domain.getBarcode().value())
                .baseUom(domain.getBaseUom().symbol())
                .itemCategory(domain.getItemCategory().name())
                .temperatureZone(domain.getTemperatureZone().name())
                .requiresExpiry(domain.isRequiresExpiry())
                .safetyStock(domain.getSafetyStock())
                .active(domain.isActive())
                .eaToGram(domain.getUomConversionProfile().getFactorFor(Uom.of("g")))
                .eaToMl(domain.getUomConversionProfile().getFactorFor(Uom.of("ml")))
                .eaToBox(domain.getUomConversionProfile().getFactorFor(Uom.of("box")))
                .eaToPlt(domain.getUomConversionProfile().getFactorFor(Uom.of("plt")))
                .createdAt(domain.getAuditInfo().createdAt())
                .createdBy(domain.getAuditInfo().createdBy())
                .lastModifiedAt(domain.getAuditInfo().lastModifiedAt())
                .lastModifiedBy(domain.getAuditInfo().lastModifiedBy())
                .build();
    }

    private UomConversionProfile mapUomConversionProfile(ItemEntity entity) {
        Map<Uom, BigDecimal> factors = new HashMap<>();
        // Assuming baseUom is EA for these factors
        if (entity.getEaToGram() != null) factors.put(Uom.of("g"), entity.getEaToGram());
        if (entity.getEaToMl() != null) factors.put(Uom.of("ml"), entity.getEaToMl());
        if (entity.getEaToBox() != null) factors.put(Uom.of("box"), entity.getEaToBox());
        if (entity.getEaToPlt() != null) factors.put(Uom.of("plt"), entity.getEaToPlt());
        return new UomConversionProfile(factors);
    }
}