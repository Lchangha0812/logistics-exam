package io.lchangha.logisticsexam.masterdata.location.web;

import io.lchangha.logisticsexam.masterdata.location.domain.Location;
import io.lchangha.logisticsexam.masterdata.item.domain.vo.ItemCategory;
import io.lchangha.logisticsexam.masterdata.location.web.dto.CreateLocationRequest;
import io.lchangha.logisticsexam.masterdata.location.web.dto.LocationResponse;
import io.lchangha.logisticsexam.masterdata.location.web.dto.UpdateLocationRequest;
import io.lchangha.logisticsexam.masterdata.vo.TemperatureZone;
import io.lchangha.logisticsexam.shared.domain.AuditInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface LocationDtoMapper {

    @Mappings({
            @Mapping(source = "auditInfo.createdAt", target = "createdAt"),
            @Mapping(source = "auditInfo.createdBy", target = "createdBy"),
            @Mapping(source = "auditInfo.lastModifiedAt", target = "lastModifiedAt"),
            @Mapping(source = "auditInfo.lastModifiedBy", target = "lastModifiedBy"),
            @Mapping(source = "type", target = "type"),
            @Mapping(source = "allowedTemperatureZone", target = "allowedTemperatureZone"),
            @Mapping(source = "allowedCategories", target = "allowedCategories")
    })
    LocationResponse toResponse(Location location);

    default Location toDomain(CreateLocationRequest request, Long newId, String creator) {
        return Location.builder()
                .id(newId)
                .code(request.code())
                .name(request.name())
                .type(Location.Type.valueOf(request.type().toUpperCase()))
                .parentId(request.parentId())
                .active(request.active())
                .allowedTemperatureZone(TemperatureZone.valueOf(request.allowedTemperatureZone().toUpperCase()))
                .allowedCategories(request.allowedCategories().stream().map(cat -> ItemCategory.valueOf(cat.toUpperCase())).toList())
                .auditInfo(AuditInfo.forCreation(creator))
                .build();
    }

    default Location toDomain(UpdateLocationRequest request, Location existingLocation, String updater) {
        return Location.builder()
                .id(existingLocation.getId())
                .code(request.code())
                .name(request.name())
                .type(Location.Type.valueOf(request.type().toUpperCase()))
                .parentId(request.parentId())
                .active(request.active())
                .allowedTemperatureZone(TemperatureZone.valueOf(request.allowedTemperatureZone().toUpperCase()))
                .allowedCategories(request.allowedCategories().stream().map(cat -> ItemCategory.valueOf(cat.toUpperCase())).toList())
                .auditInfo(existingLocation.getAuditInfo().forUpdate(updater))
                .build();
    }
}