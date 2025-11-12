package io.lchangha.logisticsexam.masterdata.domain;

import io.lchangha.logisticsexam.masterdata.domain.vo.ItemCategory;
import io.lchangha.logisticsexam.masterdata.domain.vo.TemperatureZone;
import io.lchangha.logisticsexam.shared.domain.AuditInfo;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;


import java.util.Collections;
import java.util.List;

@Getter
public class Location {
    public enum Type {WH, BIN, STAGE, REJECT} // stage는 임시 reject는 반품용도

    private Long id;
    private String code;
    private String name;
    private Type type;
    private Long parentId;
    private boolean active; // 나중에 enum
    private AuditInfo auditInfo;
    private TemperatureZone allowedTemperatureZone;
    private List<ItemCategory> allowedCategories;

    @Builder
    private Location(Long id, String code, String name, Type type, Long parentId, boolean active, AuditInfo auditInfo,
                     TemperatureZone allowedTemperatureZone, List<ItemCategory> allowedCategories) {
        Assert.hasText(code, "로케이션 코드는 비어 있을 수 없습니다.");
        Assert.hasText(name, "로케이션 이름은 비어 있을 수 없습니다.");
        Assert.notNull(type, "로케이션 타입은 null일 수 없습니다.");

        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.parentId = parentId;
        this.active = active;
        this.auditInfo = auditInfo;
        this.allowedTemperatureZone = allowedTemperatureZone;
        this.allowedCategories = Collections.unmodifiableList(allowedCategories);
    }

    /**
     * 이 로케이션에 특정 속성의 상품을 보관할 수 있는지 확인합니다.
     * (단, 이 로케이션의 allowedTemperatureZone은 null이 아니고, allowedCategories는 비어있지 않다고 가정합니다.)
     *
     * @param itemTemperatureZone 상품의 온도대
     * @param itemCategory        상품의 카테고리
     * @return 보관 가능하면 true, 불가능하면 false
     */
    public boolean canStore(TemperatureZone itemTemperatureZone, ItemCategory itemCategory) {
        // 온도대 제약 조건 확인
        if (this.allowedTemperatureZone != itemTemperatureZone) {
            return false;
        }

        // 카테고리 제약 조건 확인
        if (!this.allowedCategories.contains(itemCategory)) {
            return false;
        }

        return true;
    }
}