package io.lchangha.logisticsexam.receiving.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ReconcileReceiptRequest(
        @NotNull Long poId,
        @NotEmpty @Valid List<LineLinkRequest> links
) {
    public ReconcileReceiptRequest {
        links = links == null ? List.of() : List.copyOf(links);
        if (links.isEmpty()) {
            throw new IllegalArgumentException("links는 비어 있을 수 없습니다.");
        }
    }
}
