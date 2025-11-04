package io.lchangha.logisticsexam.domain.masterdata.partner;

import lombok.Getter;

@Getter
public enum UnitOfMeasure {
    EA("개"), // Each
    BOX("박스"), // Box
    PALLET("팔레트"), // Pallet
    KILOGRAM("킬로그램"); // Kilogram

    private final String description;

    UnitOfMeasure(String description) {
        this.description = description;
    }

}
