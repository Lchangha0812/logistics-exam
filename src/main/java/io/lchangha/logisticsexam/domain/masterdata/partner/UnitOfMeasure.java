package io.lchangha.logisticsexam.domain.masterdata.partner;

import lombok.Getter;

@Getter
public enum UnitOfMeasure {
    EA("개"),
    BOX("박스"),
    PALLET("팔레트"),
    KILOGRAM("킬로그램");

    private final String description;

    UnitOfMeasure(String description) {
        this.description = description;
    }

}
