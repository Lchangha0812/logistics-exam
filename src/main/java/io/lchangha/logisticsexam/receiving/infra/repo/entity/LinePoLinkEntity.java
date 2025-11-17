package io.lchangha.logisticsexam.receiving.infra.repo.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "line_po_links")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinePoLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goods_receipt_line_id", nullable = false)
    private GoodsReceiptLineEntity goodsReceiptLine;

    @Column(name = "po_line_id", nullable = false)
    private Long poLineId;

    @Column(nullable = false)
    private BigDecimal quantity;

    @Builder
    public LinePoLinkEntity(Long id, GoodsReceiptLineEntity goodsReceiptLine, Long poLineId, BigDecimal quantity) {
        this.id = id;
        this.goodsReceiptLine = goodsReceiptLine;
        this.poLineId = poLineId;
        this.quantity = quantity;
    }

    public void setGoodsReceiptLine(GoodsReceiptLineEntity goodsReceiptLine) {
        this.goodsReceiptLine = goodsReceiptLine;
    }
}
