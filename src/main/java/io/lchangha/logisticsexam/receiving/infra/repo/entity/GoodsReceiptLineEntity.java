package io.lchangha.logisticsexam.receiving.infra.repo.entity;

import io.lchangha.logisticsexam.receiving.domain.vo.DiscrepancyCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goods_receipt_lines")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsReceiptLineEntity {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_receipt_id", nullable = false)
    private GoodsReceiptEntity goodsReceipt;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(nullable = false)
    private String uom;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Column(name = "to_location_id", nullable = false)
    private Long toLocationId;

    @Column(name = "lot_code")
    private String lotCode;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "requires_expiry", nullable = false)
    private boolean requiresExpiry;

    @Column(name = "free_line", nullable = false)
    private boolean freeLine;

    @Column(name = "ordered_qty")
    private BigDecimal orderedQty;

    @Enumerated(EnumType.STRING)
    @Column(name = "discrepancy_code")
    private DiscrepancyCode discrepancyCode;

    @Column(name = "discrepancy_note")
    private String discrepancyNote;

    @OneToMany(mappedBy = "goodsReceiptLine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<LinePoLinkEntity> poLinks = new ArrayList<>();

    @Builder
    public GoodsReceiptLineEntity(Long id, GoodsReceiptEntity goodsReceipt, Long itemId, String uom, BigDecimal quantity, Long toLocationId, String lotCode, LocalDate expiryDate, boolean requiresExpiry, boolean freeLine, BigDecimal orderedQty, DiscrepancyCode discrepancyCode, String discrepancyNote, List<LinePoLinkEntity> poLinks) {
        this.id = id;
        this.goodsReceipt = goodsReceipt;
        this.itemId = itemId;
        this.uom = uom;
        this.quantity = quantity;
        this.toLocationId = toLocationId;
        this.lotCode = lotCode;
        this.expiryDate = expiryDate;
        this.requiresExpiry = requiresExpiry;
        this.freeLine = freeLine;
        this.orderedQty = orderedQty;
        this.discrepancyCode = discrepancyCode;
        this.discrepancyNote = discrepancyNote;
        this.poLinks = poLinks;
    }

    public void setGoodsReceipt(GoodsReceiptEntity goodsReceipt) {
        this.goodsReceipt = goodsReceipt;
    }
}
