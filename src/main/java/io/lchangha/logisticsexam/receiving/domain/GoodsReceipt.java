package io.lchangha.logisticsexam.receiving.domain;

import io.lchangha.logisticsexam.receiving.domain.command.*;
import io.lchangha.logisticsexam.receiving.domain.vo.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class GoodsReceipt {
    private final Long id;
    private final GoodsReceiptNumber grnNumber;
    private GoodsReceiptType type;
    private GoodsReceiptStatus status;
    private Long supplierId;
    private Long poId;
    private final List<GoodsReceiptLine> lines;
    private final PoTolerancePolicy tolerancePolicy;
    private final LocalDateTime receivedAt;

    @Builder(access = AccessLevel.PACKAGE)
    private GoodsReceipt(Long id,
                         GoodsReceiptNumber grnNumber,
                         GoodsReceiptType type,
                         Long supplierId,
                         Long poId,
                         PoTolerancePolicy tolerancePolicy,
                         GoodsReceiptStatus status,
                         List<GoodsReceiptLine> lines,
                         LocalDateTime receivedAt) {
        Assert.notNull(id, "GRN ID가 null일 수 없습니다.");
        Assert.notNull(grnNumber, "GRN 번호가 null일 수 없습니다.");
        Assert.notNull(type, "GRN 유형이 null일 수 없습니다.");
        Assert.notNull(status, "GRN 상태가 null일 수 없습니다.");
        Assert.notNull(receivedAt, "입고 일시가 null일 수 없습니다.");

        this.id = id;
        this.grnNumber = grnNumber;
        this.type = type;
        this.supplierId = supplierId;
        this.poId = poId;
        this.tolerancePolicy = tolerancePolicy;
        this.status = status;
        this.lines = lines == null ? new ArrayList<>() : new ArrayList<>(lines);
        this.receivedAt = receivedAt;
    }

    public static GoodsReceipt reconstitute(Long id,
                                            GoodsReceiptNumber grnNumber,
                                            GoodsReceiptType type,
                                            GoodsReceiptStatus status,
                                            Long supplierId,
                                            Long poId,
                                            List<GoodsReceiptLine> lines,
                                            PoTolerancePolicy tolerancePolicy,
                                            LocalDateTime receivedAt) {
        return new GoodsReceipt(id, grnNumber, type, supplierId, poId, tolerancePolicy, status, lines, receivedAt);
    }

    public static GoodsReceipt createFromPo(GoodsReceiptCreateFromPoParams params) {
        goodsReceiptParamGuard(params);
        return GoodsReceipt.builder()
                .id(params.id())
                .grnNumber(params.grnNumber())
                .type(GoodsReceiptType.PO_BASED)
                .status(GoodsReceiptStatus.DRAFT)
                .supplierId(params.supplierId())
                .poId(params.poId())
                .tolerancePolicy(params.tolerancePolicy())
                .lines(params.initialLines())
                .receivedAt(params.receivedAt())
                .build();
    }

    public static GoodsReceipt createFreeReceipt(GoodsReceiptCreateFreeParams params) {
        goodsReceiptParamGuard(params);
        return GoodsReceipt.builder()
                .id(params.id())
                .grnNumber(params.grnNumber())
                .type(GoodsReceiptType.FREE)
                .status(GoodsReceiptStatus.DRAFT)
                .supplierId(params.supplierId())
                .lines(params.initialLines())
                .receivedAt(params.receivedAt())
                .build();
    }

    private static void goodsReceiptParamGuard(Object params) {
        Assert.notNull(params, "생성 파라미터가 null일 수 없습니다.");
    }

    public GoodsReceiptLine addFreeLine(GoodsReceiptLineFreeLineParams params) {
        ensureDraft();
        GoodsReceiptLine line = GoodsReceiptLine.freeLine(params);
        this.lines.add(line);
        return line;
    }

    public GoodsReceiptLine addLineFromPo(GoodsReceiptLinePoLineParams params) {
        ensureDraft();
        GoodsReceiptLine line = GoodsReceiptLine.fromPo(params);
        this.lines.add(line);
        return line;
    }

    public void removeLine(GoodsReceiptLineId lineId) {
        ensureDraft();
        boolean removed = this.lines.removeIf(line -> Objects.equals(line.getId(), lineId));
        if (!removed) {
            throw new IllegalArgumentException("존재하지 않는 라인입니다. lineId=" + lineId.value());
        }
    }

    public void changeLineQuantity(GoodsReceiptLineId lineId, BigDecimal newQty) {
        ensureDraft();
        findLine(lineId).changeQuantity(newQty);
    }

    public void changeLineLocation(GoodsReceiptLineId lineId, Long locationId) {
        ensureDraft();
        findLine(lineId).changeLocation(locationId);
    }

    public void setLineLotInfo(GoodsReceiptLineId lineId, LotInfo lotInfo) {
        ensureDraft();
        findLine(lineId).updateLotInfo(lotInfo);
    }

    public GoodsReceiptLine scanIncrement(GoodsReceiptScanParams params) {
        ensureDraft();
        GoodsReceiptLine existing = findLineForScan(params);
        if (existing != null) {
            existing.increment(params.incrementQty());
            return existing;
        }
        if (params.poLineId() != null) {
            return addLineFromPo(GoodsReceiptLinePoLineParams.builder()
                    .id(params.newLineId())
                    .itemId(params.itemId())
                    .poLineId(params.poLineId())
                    .quantity(params.incrementQty())
                    .orderedQty(params.orderedQty())
                    .uom(params.uom())
                    .toLocationId(params.locationId())
                    .lotInfo(params.lotInfo())
                    .requiresExpiry(params.requiresExpiry())
                    .build()
            );
        }
        return addFreeLine(GoodsReceiptLineFreeLineParams.builder()
                .id(params.newLineId())
                .itemId(params.itemId())
                .quantity(params.incrementQty())
                .uom(params.uom())
                .toLocationId(params.locationId())
                .lotInfo(params.lotInfo())
                .requiresExpiry(params.requiresExpiry())
                .build()
        );
    }

    public void markLineDiscrepancy(GoodsReceiptLineId lineId, ReceiptLineDiscrepancy discrepancy) {
        findLine(lineId).markDiscrepancy(discrepancy);
    }

    public void linkLineToPo(GoodsReceiptLineId lineId, Long poLineId, BigDecimal qty) {
        findLine(lineId).linkToPo(poLineId, qty);
    }

    public void unlinkLineFromPo(GoodsReceiptLineId lineId, Long poLineId) {
        findLine(lineId).unlinkFromPo(poLineId);
    }

    public void reconcileToPo(Long newPoId) {
        Assert.notNull(newPoId, "PO ID가 null일 수 없습니다.");
        if (isAlreadyReconciledToDifferentPo(newPoId)) {
            throw new IllegalStateException("이미 다른 PO와 연결된 입고입니다.");
        }
        this.poId = newPoId;
        this.type = GoodsReceiptType.PO_BASED;
    }

    private boolean isAlreadyReconciledToDifferentPo(Long newPoId) {
        return this.poId != null && !this.poId.equals(newPoId);
    }

    public void validateReadyToPost(LocalDateTime now, boolean allowExtraItems) {
        ensureDraft();
        for (GoodsReceiptLine line : lines) {
            if (isMissingExpiryInfo(line)) {
                throw new IllegalStateException("유통기한 정보가 필요한 품목입니다. lineId=" + line.getId().value());
            }
            if (isUnlinkedPoLineNotAllowed(line, allowExtraItems)) {
                throw new IllegalStateException("PO 기반 입고는 PO 라인 연결이 필요합니다.");
            }
            if (isOutsidePoTolerance(line)) {
                throw new IllegalStateException("PO 허용 오차를 벗어난 라인이 있습니다. lineId=" + line.getId().value());
            }
        }
    }

    private boolean isMissingExpiryInfo(GoodsReceiptLine line) {
        return line.requiresExpiryInfo() && (line.getLotInfo() == null || line.getLotInfo().expiryDate() == null);
    }

    private boolean isUnlinkedPoLineNotAllowed(GoodsReceiptLine line, boolean allowExtraItems) {
        boolean isPoBased = type == GoodsReceiptType.PO_BASED;
        boolean hasNoPoLink = line.getPrimaryPoLine() == null;
        return !allowExtraItems && isPoBased && hasNoPoLink;
    }

    private boolean isOutsidePoTolerance(GoodsReceiptLine line) {
        boolean isPoBased = type == GoodsReceiptType.PO_BASED;
        boolean hasTolerancePolicy = tolerancePolicy != null;
        boolean hasOrderedQty = line.getOrderedQty() != null;

        if (isPoBased && hasTolerancePolicy && hasOrderedQty) {
            return !tolerancePolicy.isWithinTolerance(line.getOrderedQty(), line.getQuantity());
        }
        return false;
    }

    public void markPosted() {
        ensureDraft();
        this.status = GoodsReceiptStatus.POSTED;
    }

    private GoodsReceiptLine findLine(GoodsReceiptLineId lineId) {
        return lines.stream()
                .filter(line -> Objects.equals(line.getId(), lineId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("라인을 찾을 수 없습니다. lineId=" + lineId.value()));
    }

    private GoodsReceiptLine findLineForScan(GoodsReceiptScanParams params) {
        return lines.stream()
                .filter(line -> line.matchesForScan(
                        params.itemId(),
                        params.locationId(),
                        params.lotInfo(),
                        params.poLineId()))
                .findFirst()
                .orElse(null);
    }

    private void ensureDraft() {
        Assert.state(this.status == GoodsReceiptStatus.DRAFT, "DRAFT 상태에서만 수행할 수 있습니다.");
    }

    public List<GoodsReceiptLine> getLines() {
        return Collections.unmodifiableList(lines);
    }
}
