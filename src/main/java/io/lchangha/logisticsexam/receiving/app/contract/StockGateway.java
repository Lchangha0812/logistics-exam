package io.lchangha.logisticsexam.receiving.app.contract;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceipt;

public interface StockGateway {
    void post(GoodsReceipt receipt);
}
