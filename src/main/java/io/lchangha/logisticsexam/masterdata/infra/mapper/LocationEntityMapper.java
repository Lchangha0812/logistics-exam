package io.lchangha.logisticsexam.masterdata.infra.mapper;

import io.lchangha.logisticsexam.masterdata.domain.Location;
import io.lchangha.logisticsexam.masterdata.domain.vo.ItemCategory;
import io.lchangha.logisticsexam.masterdata.domain.vo.TemperatureZone;
import io.lchangha.logisticsexam.masterdata.infra.entity.LocationEntity;
import io.lchangha.logisticsexam.shared.domain.AuditInfo;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LocationEntityMapper {

    private static final String CATEGORY_DELIMITER = ",";

    public Location toDomain(LocationEntity entity) {
        AuditInfo auditInfo = new AuditInfo(
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getLastModifiedAt(),
                entity.getLastModifiedBy()
        );

        return Location.builder()
                .id(entity.getId())
                .code(entity.getCode())
                .name(entity.getName())
                .type(Location.Type.valueOf(entity.getType().toUpperCase()))
                .parentId(entity.getParentId())
                .active(entity.isActive())
                .allowedTemperatureZone(TemperatureZone.valueOf(entity.getAllowedTemperatureZone().toUpperCase()))
                .allowedCategories(mapToItemCategoryList(entity.getAllowedCategories()))
                .auditInfo(auditInfo)
                .build();
    }

    public LocationEntity toEntity(Location domain) {
        return LocationEntity.builder()
                .id(domain.getId())
                .code(domain.getCode())
                .name(domain.getName())
                .type(domain.getType().name())
                .parentId(domain.getParentId())
                .active(domain.isActive())
                .allowedTemperatureZone(domain.getAllowedTemperatureZone().name())
                .allowedCategories(mapToString(domain.getAllowedCategories()))
                .createdAt(domain.getAuditInfo().createdAt())
                .createdBy(domain.getAuditInfo().createdBy())
                .lastModifiedAt(domain.getAuditInfo().lastModifiedAt())
                .lastModifiedBy(domain.getAuditInfo().lastModifiedBy())
                .build();
    }

    private List<ItemCategory> mapToItemCategoryList(String categories) {
        // 도메인에서 유효성 검증을 하므로, 여기서는 null/empty 체크를 하지 않습니다.
        return Arrays.stream(categories.split(CATEGORY_DELIMITER))
                .map(String::trim)
                .map(cat -> ItemCategory.valueOf(cat.toUpperCase()))
                .toList();
    }

    private String mapToString(List<ItemCategory> categories) {
        // 도메인에서 유효성 검증을 하므로, 여기서는 null/empty 체크를 하지 않습니다.
        return categories.stream()
                .map(Enum::name)
                .collect(Collectors.joining(CATEGORY_DELIMITER));
    }
}