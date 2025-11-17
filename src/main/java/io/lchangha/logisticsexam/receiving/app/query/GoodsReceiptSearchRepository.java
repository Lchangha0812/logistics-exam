package io.lchangha.logisticsexam.receiving.app.query;

import io.lchangha.logisticsexam.shared.domain.page.PageResult;

public interface GoodsReceiptSearchRepository {
    PageResult<GoodsReceiptListRow> search(GoodsReceiptSearchFilter filter);
}
