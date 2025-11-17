package io.lchangha.logisticsexam.receiving.app;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceipt;
import io.lchangha.logisticsexam.receiving.domain.command.GoodsReceiptLineFreeLineParams;
import io.lchangha.logisticsexam.receiving.domain.command.GoodsReceiptLinePoLineParams;
import io.lchangha.logisticsexam.receiving.domain.command.GoodsReceiptScanParams;
import io.lchangha.logisticsexam.receiving.domain.contract.GoodsReceiptLineIdGenerator;
import io.lchangha.logisticsexam.receiving.domain.contract.GoodsReceiptRepository;
import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptLineId;
import io.lchangha.logisticsexam.receiving.domain.vo.ReceiptLineDiscrepancy;
import io.lchangha.logisticsexam.receiving.app.mapper.GoodsReceiptCreationMapper;
import io.lchangha.logisticsexam.receiving.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsReceiptLineService {
    private final GoodsReceiptRepository goodsReceiptRepository;
    private final GoodsReceiptCreationMapper goodsReceiptCreationMapper;

    public void addFreeLine(Long grnId, AddFreeLineRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        GoodsReceiptLineFreeLineParams params = GoodsReceiptLineFreeLineParams.builder()
                .itemId(request.itemId())
                .quantity(request.quantity())
                .uom(request.uom())
                .toLocationId(request.toLocationId())
                .lotInfo(goodsReceiptCreationMapper.toLotInfo(request.lotInfo()))
                .requiresExpiry(Boolean.TRUE.equals(request.requiresExpiry()))
                .build();
        receipt.addFreeLine(params);
        goodsReceiptRepository.save(receipt);
    }

    public void addPoLine(Long grnId, AddPoLineRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        GoodsReceiptLinePoLineParams params = GoodsReceiptLinePoLineParams.builder()
                .itemId(request.itemId())
                .poLineId(request.poLineId())
                .quantity(request.quantity())
                .orderedQty(request.orderedQty())
                .uom(request.uom())
                .toLocationId(request.toLocationId())
                .lotInfo(goodsReceiptCreationMapper.toLotInfo(request.lotInfo()))
                .requiresExpiry(Boolean.TRUE.equals(request.requiresExpiry()))
                .build();
        receipt.addLineFromPo(params);
        goodsReceiptRepository.save(receipt);
    }

    public void scanReceiveItem(Long grnId, ScanReceiveItemRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        receipt.scanIncrement(GoodsReceiptScanParams.builder()
                .itemId(request.itemId())
                .incrementQty(request.quantity())
                .uom(request.uom())
                .locationId(request.toLocationId())
                .lotInfo(goodsReceiptCreationMapper.toLotInfo(request.lotInfo()))
                .poLineId(request.poLineId())
                .orderedQty(request.poLineId() == null ? null : request.orderedQty())
                .requiresExpiry(Boolean.TRUE.equals(request.requiresExpiry()))
                .build()
        );
        goodsReceiptRepository.save(receipt);
    }

    public void changeLineQuantity(Long grnId, ChangeLineQuantityRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        receipt.changeLineQuantity(new GoodsReceiptLineId(request.lineId()), request.newQuantity());
        goodsReceiptRepository.save(receipt);
    }

    public void changeLineLocation(Long grnId, ChangeLineLocationRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        receipt.changeLineLocation(new GoodsReceiptLineId(request.lineId()), request.newLocationId());
        goodsReceiptRepository.save(receipt);
    }

    public void setLineLot(Long grnId, SetLineLotRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        receipt.setLineLotInfo(new GoodsReceiptLineId(request.lineId()), goodsReceiptCreationMapper.toLotInfo(request.lotInfo()));
        goodsReceiptRepository.save(receipt);
    }

    public void removeLine(Long grnId, Long lineId) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        receipt.removeLine(new GoodsReceiptLineId(lineId));
        goodsReceiptRepository.save(receipt);
    }

    public void markDiscrepancy(Long grnId, MarkDiscrepancyRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        ReceiptLineDiscrepancy discrepancy = new ReceiptLineDiscrepancy(
                request.discrepancyCode(),
                request.note()
        );
        receipt.markLineDiscrepancy(new GoodsReceiptLineId(request.lineId()), discrepancy);
        goodsReceiptRepository.save(receipt);
    }
}
