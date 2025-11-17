package io.lchangha.logisticsexam.receiving.app;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceipt;
import io.lchangha.logisticsexam.receiving.domain.contract.GoodsReceiptRepository;
import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptLineId;
import io.lchangha.logisticsexam.receiving.web.dto.LineLinkRequest;
import io.lchangha.logisticsexam.receiving.web.dto.PostGoodsReceiptRequest;
import io.lchangha.logisticsexam.receiving.web.dto.ReconcileReceiptRequest;
import io.lchangha.logisticsexam.receiving.web.dto.UnlinkLineRequest;
import io.lchangha.logisticsexam.receiving.app.contract.StockGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsReceiptLifecycleService {
    private final GoodsReceiptRepository goodsReceiptRepository;
    private final StockGateway stockGateway;

    public void postGoodsReceipt(Long grnId, PostGoodsReceiptRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        receipt.validateReadyToPost(LocalDateTime.now(), Boolean.TRUE.equals(request.allowExtraItems()));
        receipt.markPosted();
        stockGateway.post(receipt);
        goodsReceiptRepository.save(receipt);
    }

    public void reconcileReceiptToPo(Long grnId, ReconcileReceiptRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        receipt.reconcileToPo(request.poId());
        for (LineLinkRequest linkRequest : request.links()) {
            receipt.linkLineToPo(
                    new GoodsReceiptLineId(linkRequest.lineId()),
                    linkRequest.poLineId(),
                    linkRequest.quantity()
            );
        }
        goodsReceiptRepository.save(receipt);
    }

    public void linkReceiptLineToPo(Long grnId, LineLinkRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        receipt.linkLineToPo(
                new GoodsReceiptLineId(request.lineId()),
                request.poLineId(),
                request.quantity()
        );
        goodsReceiptRepository.save(receipt);
    }

    public void unlinkReceiptLineFromPo(Long grnId, UnlinkLineRequest request) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));

        receipt.unlinkLineFromPo(new GoodsReceiptLineId(request.lineId()), request.poLineId());
        goodsReceiptRepository.save(receipt);
    }
}
