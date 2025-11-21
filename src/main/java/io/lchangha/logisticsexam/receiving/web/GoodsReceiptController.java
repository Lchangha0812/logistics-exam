package io.lchangha.logisticsexam.receiving.web;

import io.lchangha.logisticsexam.receiving.app.GoodsReceiptCreationService;
import io.lchangha.logisticsexam.receiving.app.GoodsReceiptLifecycleService;
import io.lchangha.logisticsexam.receiving.app.GoodsReceiptLineService;
import io.lchangha.logisticsexam.receiving.app.query.GoodsReceiptSearchService;
import io.lchangha.logisticsexam.receiving.app.query.ReceivingVarianceQueryService;
import io.lchangha.logisticsexam.receiving.web.dto.*;
import io.lchangha.logisticsexam.shared.web.dto.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/receiving/grns")
@RequiredArgsConstructor
public class GoodsReceiptController {

    private final GoodsReceiptCreationService goodsReceiptCreationService;
    private final GoodsReceiptLineService goodsReceiptLineService;
    private final GoodsReceiptLifecycleService goodsReceiptLifecycleService;
    private final ReceivingVarianceQueryService receivingVarianceQueryService;
    private final GoodsReceiptSearchService goodsReceiptSearchService;

    @PostMapping("/po")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateGoodsReceiptResponse createFromPo(@Valid @RequestBody CreateGoodsReceiptFromPoRequest request) {
        return goodsReceiptCreationService.createGoodsReceiptFromPo(request, "");
    }

    @PostMapping("/free")
    @ResponseStatus(HttpStatus.CREATED)
    public CreateGoodsReceiptResponse createFree(@Valid @RequestBody CreateFreeGoodsReceiptRequest request) {
        return goodsReceiptCreationService.createFreeGoodsReceipt(request, "");
    }

    @GetMapping("/{grnId}")
    public GoodsReceiptResponse getReceipt(@PathVariable Long grnId) {
        return goodsReceiptCreationService.getGoodsReceipt(grnId);
    }

    @PostMapping("/{grnId}/lines/free")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFreeLine(@PathVariable Long grnId, @Valid @RequestBody AddFreeLineRequest request) {
        goodsReceiptLineService.addFreeLine(grnId, request);
    }

    @PostMapping("/{grnId}/lines/po")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPoLine(@PathVariable Long grnId, @Valid @RequestBody AddPoLineRequest request) {
        goodsReceiptLineService.addPoLine(grnId, request);
    }

    @PostMapping("/{grnId}/scan")
    @ResponseStatus(HttpStatus.CREATED)
    public void scanReceiveItem(@PathVariable Long grnId, @Valid @RequestBody ScanReceiveItemRequest request) {
        goodsReceiptLineService.scanReceiveItem(grnId, request);
    }

    @PatchMapping("/{grnId}/lines/{lineId}/quantity")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeLineQuantity(@PathVariable Long grnId,
                                   @PathVariable Long lineId,
                                   @Valid @RequestBody ChangeLineQuantityRequest request) {
        validateLineId(lineId, request.lineId());
        goodsReceiptLineService.changeLineQuantity(grnId, request);
    }

    @PatchMapping("/{grnId}/lines/{lineId}/location")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeLineLocation(@PathVariable Long grnId,
                                   @PathVariable Long lineId,
                                   @Valid @RequestBody ChangeLineLocationRequest request) {
        validateLineId(lineId, request.lineId());
        goodsReceiptLineService.changeLineLocation(grnId, request);
    }

    @PatchMapping("/{grnId}/lines/{lineId}/lot")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setLineLot(@PathVariable Long grnId,
                           @PathVariable Long lineId,
                           @Valid @RequestBody SetLineLotRequest request) {
        validateLineId(lineId, request.lineId());
        goodsReceiptLineService.setLineLot(grnId, request);
    }

    @DeleteMapping("/{grnId}/lines/{lineId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLine(@PathVariable Long grnId, @PathVariable Long lineId) {
        goodsReceiptLineService.removeLine(grnId, lineId);
    }

    @PostMapping("/{grnId}/lines/{lineId}/discrepancy")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void markDiscrepancy(@PathVariable Long grnId,
                                @PathVariable Long lineId,
                                @Valid @RequestBody MarkDiscrepancyRequest request) {
        validateLineId(lineId, request.lineId());
        goodsReceiptLineService.markDiscrepancy(grnId, request);
    }

    @PostMapping("/{grnId}/post")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postReceipt(@PathVariable Long grnId,
                            @Valid @RequestBody PostGoodsReceiptRequest request) {
        goodsReceiptLifecycleService.postGoodsReceipt(grnId, request);
    }

    @PostMapping("/{grnId}/reconcile")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reconcileToPo(@PathVariable Long grnId,
                              @Valid @RequestBody ReconcileReceiptRequest request) {
        goodsReceiptLifecycleService.reconcileReceiptToPo(grnId, request);
    }

    @PostMapping("/{grnId}/lines/{lineId}/link")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void linkLineToPo(@PathVariable Long grnId,
                             @PathVariable Long lineId,
                             @Valid @RequestBody LineLinkRequest request) {
        validateLineId(lineId, request.lineId());
        goodsReceiptLifecycleService.linkReceiptLineToPo(grnId, request);
    }

    @PostMapping("/{grnId}/lines/{lineId}/unlink")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlinkLineFromPo(@PathVariable Long grnId,
                                 @PathVariable Long lineId,
                                 @Valid @RequestBody UnlinkLineRequest request) {
        validateLineId(lineId, request.lineId());
        goodsReceiptLifecycleService.unlinkReceiptLineFromPo(grnId, request);
    }

    @PostMapping("/variance")
    public ReceivingVarianceReportResponse getVarianceReport(@Valid @RequestBody ReceivingVarianceReportRequest request) {
        return receivingVarianceQueryService.getVarianceReport(request);
    }

    @PostMapping("/search")
    public PageResponse<SearchGoodsReceiptsRow> search(@Valid @RequestBody SearchGoodsReceiptsRequest request,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        return goodsReceiptSearchService.search(request, page, size);
    }

    private void validateLineId(Long pathLineId, Long bodyLineId) {
        if (!pathLineId.equals(bodyLineId)) {
            throw new IllegalArgumentException("경로 lineId와 본문 lineId가 일치하지 않습니다");
        }
    }
}


