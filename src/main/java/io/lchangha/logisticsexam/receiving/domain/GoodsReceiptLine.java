package io.lchangha.logisticsexam.receiving.domain;

import io.lchangha.logisticsexam.receiving.domain.command.GoodsReceiptLineFreeLineParams;
import io.lchangha.logisticsexam.receiving.domain.command.GoodsReceiptLinePoLineParams;
import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptLineId;
import io.lchangha.logisticsexam.receiving.domain.vo.LinePoLink;
import io.lchangha.logisticsexam.receiving.domain.vo.LotInfo;
import io.lchangha.logisticsexam.receiving.domain.vo.ReceiptLineDiscrepancy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class GoodsReceiptLine {
    private GoodsReceiptLineId id;
    private Long itemId;
    private String uom;
    private BigDecimal quantity;
    private Long toLocationId;
    private LotInfo lotInfo;
    private boolean requiresExpiry;
    private boolean freeLine;
    private BigDecimal orderedQty;
    private ReceiptLineDiscrepancy discrepancy;
    private List<LinePoLink> poLinks;

    @Builder(access = AccessLevel.PACKAGE)
    private GoodsReceiptLine(GoodsReceiptLineId id,
                             Long itemId,
                             String uom,
                             BigDecimal quantity,
                             Long toLocationId,
                             LotInfo lotInfo,
                             boolean requiresExpiry,
                             boolean freeLine,
                             BigDecimal orderedQty,
                             ReceiptLineDiscrepancy discrepancy,
                             List<LinePoLink> poLinks) {
        Assert.notNull(id, "라인 ID는 null일 수 없습니다.");
        Assert.notNull(itemId, "품목 ID는 null일 수 없습니다.");
        Assert.hasText(uom, "UOM은 비어 있을 수 없습니다.");
        Assert.notNull(quantity, "수량은 null일 수 없습니다.");
        Assert.isTrue(quantity.signum() > 0, "수량은 0보다 커야 합니다.");

        this.id = id;
        this.itemId = itemId;
        this.uom = uom;
        this.quantity = quantity;
        this.toLocationId = toLocationId;
        this.lotInfo = lotInfo;
        this.requiresExpiry = requiresExpiry;
        this.freeLine = freeLine;
        this.orderedQty = orderedQty;
        this.discrepancy = discrepancy;
        this.poLinks = poLinks == null ? new ArrayList<>() : new ArrayList<>(poLinks);
    }

    public static GoodsReceiptLine reconstitute(GoodsReceiptLineId id,
                                                Long itemId,
                                                String uom,
                                                BigDecimal quantity,
                                                Long toLocationId,
                                                LotInfo lotInfo,
                                                boolean requiresExpiry,
                                                boolean freeLine,
                                                BigDecimal orderedQty,
                                                ReceiptLineDiscrepancy discrepancy,
                                                List<LinePoLink> poLinks) {
        return new GoodsReceiptLine(id, itemId, uom, quantity, toLocationId, lotInfo, requiresExpiry, freeLine, orderedQty, discrepancy, poLinks);
    }

    public static GoodsReceiptLine freeLine(GoodsReceiptLineFreeLineParams params) {
        Assert.notNull(params, "무상 라인 파라미터는 null일 수 없습니다.");
        return GoodsReceiptLine.builder()
                .id(params.id())
                .itemId(params.itemId())
                .quantity(params.quantity())
                .uom(params.uom())
                .toLocationId(params.toLocationId())
                .lotInfo(params.lotInfo())
                .requiresExpiry(params.requiresExpiry())
                .freeLine(true)
                .build();
    }

    public static GoodsReceiptLine fromPo(GoodsReceiptLinePoLineParams params) {
        Assert.notNull(params, "PO 라인 파라미터는 null일 수 없습니다.");
        Assert.notNull(params.poLineId(), "PO 라인 ID는 null일 수 없습니다.");
        LinePoLink link = new LinePoLink(params.poLineId(), params.quantity());
        return GoodsReceiptLine.builder()
                .id(params.id())
                .itemId(params.itemId())
                .quantity(params.quantity())
                .orderedQty(params.orderedQty())
                .uom(params.uom())
                .toLocationId(params.toLocationId())
                .lotInfo(params.lotInfo())
                .requiresExpiry(params.requiresExpiry())
                .freeLine(false)
                .poLinks(Collections.singletonList(link))
                .build();
    }

    public void increment(BigDecimal delta) {
        Assert.notNull(delta, "증가 수량은 null일 수 없습니다.");
        Assert.isTrue(delta.signum() > 0, "증가 수량은 0보다 커야 합니다.");
        this.quantity = this.quantity.add(delta);
        if (!freeLine && poLinks.size() == 1) {
            LinePoLink existing = poLinks.get(0);
            poLinks.set(0, new LinePoLink(existing.poLineId(), existing.quantity().add(delta)));
        }
    }

    public void changeQuantity(BigDecimal newQty) {
        Assert.notNull(newQty, "수량은 null일 수 없습니다.");
        Assert.isTrue(newQty.signum() > 0, "수량은 0보다 커야 합니다.");
        BigDecimal totalLinked = getTotalLinkedQty();
        Assert.isTrue(newQty.compareTo(totalLinked) >= 0, "이미 링크된 수량보다 적게 변경할 수 없습니다.");

        boolean isPoLineWithSingleLink = !freeLine && poLinks.size() == 1;
        if (isPoLineWithSingleLink) {
            LinePoLink existing = poLinks.get(0);
            BigDecimal others = totalLinked.subtract(existing.quantity());
            BigDecimal newPrimaryQty = newQty.subtract(others);
            Assert.isTrue(newPrimaryQty.signum() >= 0, "주 PO 링크 수량은 음수일 수 없습니다.");
            poLinks.set(0, new LinePoLink(existing.poLineId(), newPrimaryQty));
        }
        this.quantity = newQty;
    }

    public void changeLocation(Long newLocationId) {
        Assert.notNull(newLocationId, "로케이션은 null일 수 없습니다.");
        this.toLocationId = newLocationId;
    }

    public void updateLotInfo(LotInfo newLotInfo) {
        if (newLotInfo == null) {
            return;
        }
        this.lotInfo = this.lotInfo == null ? newLotInfo : this.lotInfo.merge(newLotInfo);
    }

    public void markDiscrepancy(ReceiptLineDiscrepancy discrepancy) {
        this.discrepancy = discrepancy;
    }

    public void clearDiscrepancy() {
        this.discrepancy = null;
    }

    public void linkToPo(Long poLineId, BigDecimal qty) {
        Assert.notNull(poLineId, "PO 라인 ID는 null일 수 없습니다.");
        Assert.notNull(qty, "링크 수량은 null일 수 없습니다.");
        Assert.isTrue(qty.signum() > 0, "링크 수량은 0보다 커야 합니다.");
        BigDecimal totalLinked = getTotalLinkedQty();
        Assert.isTrue(totalLinked.add(qty).compareTo(this.quantity) <= 0, "링크 수량이 라인 수량을 초과할 수 없습니다.");
        poLinks.add(new LinePoLink(poLineId, qty));
    }

    public void unlinkFromPo(Long poLineId) {
        Assert.notNull(poLineId, "PO 라인 ID는 null일 수 없습니다.");
        boolean removed = poLinks.removeIf(link -> Objects.equals(link.poLineId(), poLineId));
        Assert.isTrue(removed, "PO 링크를 찾을 수 없습니다.");
    }

    public boolean matchesForScan(Long targetItemId, Long locationId, LotInfo targetLotInfo, Long poLineId) {
        boolean isSameItem = Objects.equals(this.itemId, targetItemId);
        boolean isSameLocation = Objects.equals(this.toLocationId, locationId);
        boolean isSamePoLine = Objects.equals(this.getPrimaryPoLine(), poLineId);
        boolean isSameLot = lotMatches(targetLotInfo);

        return isSameItem && isSameLocation && isSamePoLine && isSameLot;
    }

    private boolean lotMatches(LotInfo targetLotInfo) {
        if (this.lotInfo == null) {
            return targetLotInfo == null || targetLotInfo.isEmpty();
        }
        return this.lotInfo.matches(targetLotInfo);
    }

    public BigDecimal getTotalLinkedQty() {
        return poLinks.stream()
                .map(LinePoLink::quantity)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<LinePoLink> getPoLinks() {
        return Collections.unmodifiableList(poLinks);
    }

    public Long getPrimaryPoLine() {
        return poLinks.isEmpty() ? null : poLinks.get(0).poLineId();
    }

    public boolean requiresExpiryInfo() {
        return requiresExpiry;
    }
}
