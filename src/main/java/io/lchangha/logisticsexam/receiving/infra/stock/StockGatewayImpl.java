package io.lchangha.logisticsexam.receiving.infra.stock;

import io.lchangha.logisticsexam.receiving.app.contract.StockGateway;
import io.lchangha.logisticsexam.receiving.domain.GoodsReceipt;
import io.lchangha.logisticsexam.receiving.domain.GoodsReceiptLine;
import io.lchangha.logisticsexam.stock.app.InventoryPostingService;
import io.lchangha.logisticsexam.stock.app.dto.StockPostingLine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class StockGatewayImpl implements StockGateway {

    private final InventoryPostingService inventoryPostingService;

    @Override
    public void post(GoodsReceipt receipt) {
        List<StockPostingLine> lines = receipt.getLines().stream()
                .map(line -> toPostingLine(receipt, line))
                .toList();

        inventoryPostingService.postGoodsReceipt(
                receipt.getId(),
                receipt.getType().name(),
                receipt.getReceivedAt(),
                lines
        );
    }

    private StockPostingLine toPostingLine(GoodsReceipt receipt, GoodsReceiptLine line) {
        return new StockPostingLine(
                line.getItemId(),
                line.getQuantity(),
                line.getUom(),
                line.getToLocationId(),
                line.getLotInfo() != null ? line.getLotInfo().lotCode() : null,
                line.getLotInfo() != null ? line.getLotInfo().expiryDate() : null,
                receipt.getPoId(),
                line.getPrimaryPoLine()
        );
    }
}
