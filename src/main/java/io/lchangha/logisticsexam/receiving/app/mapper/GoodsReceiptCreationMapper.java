package io.lchangha.logisticsexam.receiving.app.mapper;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceiptLine;
import io.lchangha.logisticsexam.receiving.domain.command.GoodsReceiptLineFreeLineParams;
import io.lchangha.logisticsexam.receiving.domain.command.GoodsReceiptLinePoLineParams;
import io.lchangha.logisticsexam.receiving.domain.contract.GoodsReceiptLineIdGenerator;
import io.lchangha.logisticsexam.receiving.domain.vo.LotInfo;
import io.lchangha.logisticsexam.receiving.web.dto.FreeReceiptLineRequest;
import io.lchangha.logisticsexam.receiving.web.dto.LotInfoPayload;
import io.lchangha.logisticsexam.receiving.web.dto.PoReceiptLineRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GoodsReceiptCreationMapper {

    public LotInfo toLotInfo(LotInfoPayload payload) {
        if (payload == null || payload.isEmpty()) {
            return null;
        }
        return new LotInfo(payload.lotCode(), payload.expiryDate());
    }

    public List<GoodsReceiptLine> buildPoLines(List<PoReceiptLineRequest> lineRequests) {
        List<GoodsReceiptLine> lines = new ArrayList<>();
        if (lineRequests == null) {
            return lines;
        }
        for (PoReceiptLineRequest lineRequest : lineRequests) {
            lines.add(GoodsReceiptLine.fromPo(GoodsReceiptLinePoLineParams.builder()
                    .itemId(lineRequest.itemId())
                    .poLineId(lineRequest.poLineId())
                    .quantity(lineRequest.quantity())
                    .orderedQty(lineRequest.orderedQty())
                    .uom(lineRequest.uom())
                    .toLocationId(lineRequest.toLocationId())
                    .lotInfo(toLotInfo(lineRequest.lotInfo()))
                    .requiresExpiry(Boolean.TRUE.equals(lineRequest.requiresExpiry()))
                    .build()
            ));
        }
        return lines;
    }

    public List<GoodsReceiptLine> buildFreeLines(List<FreeReceiptLineRequest> lineRequests) {
        List<GoodsReceiptLine> lines = new ArrayList<>();
        if (lineRequests == null) {
            return lines;
        }
        for (FreeReceiptLineRequest lineRequest : lineRequests) {
            lines.add(GoodsReceiptLine.freeLine(GoodsReceiptLineFreeLineParams.builder()
                    .itemId(lineRequest.itemId())
                    .quantity(lineRequest.quantity())
                    .uom(lineRequest.uom())
                    .toLocationId(lineRequest.toLocationId())
                    .lotInfo(toLotInfo(lineRequest.lotInfo()))
                    .requiresExpiry(Boolean.TRUE.equals(lineRequest.requiresExpiry()))
                    .build()
            ));
        }
        return lines;
    }
}
