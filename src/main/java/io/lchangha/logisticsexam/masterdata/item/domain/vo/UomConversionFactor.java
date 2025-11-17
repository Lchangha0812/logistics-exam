package io.lchangha.logisticsexam.masterdata.item.domain.vo;

import java.math.BigDecimal;

public record UomConversionFactor (Uom toUnit, BigDecimal toFactor) {

}
