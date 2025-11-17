package io.lchangha.logisticsexam.receiving.web.dto;

import java.math.BigDecimal;

public record LinePoLinkResponse(
        Long poLineId,
        BigDecimal quantity
) {
}
