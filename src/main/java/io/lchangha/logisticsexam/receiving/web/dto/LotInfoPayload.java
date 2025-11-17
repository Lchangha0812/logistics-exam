package io.lchangha.logisticsexam.receiving.web.dto;

import java.time.LocalDate;

public record LotInfoPayload(
        String lotCode,
        LocalDate expiryDate
) {
    public boolean isEmpty() {
        return (lotCode == null || lotCode.isBlank()) && expiryDate == null;
    }
}
