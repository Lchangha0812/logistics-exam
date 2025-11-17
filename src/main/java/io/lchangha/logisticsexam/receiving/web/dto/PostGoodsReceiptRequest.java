package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.constraints.NotNull;

public record PostGoodsReceiptRequest(
        @NotNull Boolean allowExtraItems
) {
}
