package io.lchangha.logisticsexam.shared.web.dto;

import java.util.List;

public record PageResponse<T>(
        List<T> content, int page, int size, long totalElements, int totalPages, boolean hasNext
) {
}