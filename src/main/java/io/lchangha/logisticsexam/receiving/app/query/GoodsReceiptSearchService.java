package io.lchangha.logisticsexam.receiving.app.query;

import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptStatus;
import io.lchangha.logisticsexam.receiving.web.dto.SearchGoodsReceiptsRequest;
import io.lchangha.logisticsexam.receiving.web.dto.SearchGoodsReceiptsRow;
import io.lchangha.logisticsexam.shared.web.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GoodsReceiptSearchService {
    private final GoodsReceiptSearchRepository goodsReceiptSearchRepository;

    @Transactional(readOnly = true)
    public PageResponse<SearchGoodsReceiptsRow> search(SearchGoodsReceiptsRequest request, int page, int size) {
        GoodsReceiptStatus status = request.status() == null ? null : GoodsReceiptStatus.valueOf(request.status().toUpperCase());
        GoodsReceiptSearchFilter filter = new GoodsReceiptSearchFilter(
                request.fromDate(),
                request.toDate(),
                request.supplierId(),
                request.poId(),
                request.itemId(),
                status,
                page,
                size
        );
        var result = goodsReceiptSearchRepository.search(filter);
        var rows = result.content().stream()
                .map(row -> new SearchGoodsReceiptsRow(
                        row.id(),
                        row.grnNumber(),
                        row.status(),
                        row.type(),
                        row.supplierId(),
                        row.supplierName(),
                        row.receivedAt(),
                        row.poId()
                )).toList();
        return new PageResponse<>(rows, result.page(), result.size(), result.totalElements(), result.totalPages(), result.hasNext());
    }
}
