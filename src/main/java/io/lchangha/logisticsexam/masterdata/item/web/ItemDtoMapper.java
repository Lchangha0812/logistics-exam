package io.lchangha.logisticsexam.masterdata.item.web;

import io.lchangha.logisticsexam.masterdata.item.domain.Item;
import io.lchangha.logisticsexam.masterdata.item.domain.vo.*;
import io.lchangha.logisticsexam.masterdata.item.web.dto.CreateItemRequest;
import io.lchangha.logisticsexam.masterdata.item.web.dto.ItemResponse;
import io.lchangha.logisticsexam.masterdata.item.web.dto.UomConversionProfileRequest;
import io.lchangha.logisticsexam.masterdata.item.web.dto.UpdateItemRequest;
import io.lchangha.logisticsexam.masterdata.vo.TemperatureZone;
import io.lchangha.logisticsexam.shared.domain.AuditInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ItemDtoMapper {

    @Mappings({
            @Mapping(source = "auditInfo.createdAt", target = "createdAt"),
            @Mapping(source = "auditInfo.createdBy", target = "createdBy"),
            @Mapping(source = "auditInfo.lastModifiedAt", target = "lastModifiedAt"),
            @Mapping(source = "auditInfo.lastModifiedBy", target = "lastModifiedBy"),
            @Mapping(source = "sku.value", target = "skuCode"),
            @Mapping(source = "barcode.value", target = "barcode"),
            @Mapping(source = "baseUom.symbol", target = "baseUom"),
            @Mapping(source = "itemCategory", target = "itemCategory"),
            @Mapping(source = "temperatureZone", target = "temperatureZone"),
            @Mapping(source = "uomConversionProfile", target = "uomConversionProfile")
    })
    ItemResponse toResponse(Item item);

    default ItemResponse.UomConversionProfileResponse map(UomConversionProfile profile) {
        Map<String, BigDecimal> factors = profile.factors().stream()
                .collect(Collectors.toMap(
                        factor -> factor.toUnit().symbol(),
                        UomConversionFactor::toFactor
                ));
        return new ItemResponse.UomConversionProfileResponse(factors);
    }

    default Item toDomain(CreateItemRequest request, Long newId, String creator) {
        return Item.builder()
                .id(newId)
                .name(request.name())
                .sku(new SKU(request.skuCode()))
                .barcode(new Barcode(request.barcode()))
                .baseUom(Uom.of(request.baseUom()))
                .itemCategory(ItemCategory.valueOf(request.itemCategory().toUpperCase()))
                .temperatureZone(TemperatureZone.valueOf(request.temperatureZone().toUpperCase()))
                .requiresExpiry(request.requiresExpiry())
                .safetyStock(request.safetyStock())
                .active(request.active())
                .uomConversionProfile(toUomConversionProfile(request.uomConversionProfile()))
                .auditInfo(AuditInfo.forCreation(creator))
                .build();
    }

    default Item toDomain(UpdateItemRequest request, Item existingItem, String updater) {
        return Item.builder()
                .id(existingItem.getId())
                .name(request.name())
                .sku(new SKU(request.skuCode()))
                .barcode(new Barcode(request.barcode()))
                .baseUom(Uom.of(request.baseUom()))
                .itemCategory(ItemCategory.valueOf(request.itemCategory().toUpperCase()))
                .temperatureZone(TemperatureZone.valueOf(request.temperatureZone().toUpperCase()))
                .requiresExpiry(request.requiresExpiry())
                .safetyStock(request.safetyStock())
                .active(request.active())
                .uomConversionProfile(toUomConversionProfile(request.uomConversionProfile()))
                .auditInfo(existingItem.getAuditInfo().forUpdate(updater))
                .build();
    }

    default UomConversionProfile toUomConversionProfile(UomConversionProfileRequest request) {
        Set<UomConversionFactor> factors = request.factors().stream()
                .map(factorRequest -> new UomConversionFactor(
                        Uom.of(factorRequest.name()),
                        factorRequest.factor()
                ))
                .collect(Collectors.toSet());
        return new UomConversionProfile(factors);
    }
}