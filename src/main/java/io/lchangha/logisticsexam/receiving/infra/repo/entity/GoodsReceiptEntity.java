package io.lchangha.logisticsexam.receiving.infra.repo.entity;

import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptStatus;
import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goods_receipts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodsReceiptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "grn_number", nullable = false, unique = true)
    private String grnNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoodsReceiptType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GoodsReceiptStatus status;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "po_id")
    private Long poId;

    @Column(name = "po_tolerance_policy_id")
    private Long poTolerancePolicyId;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false, updatable = false)
    private String createdBy;

    @Column(name = "last_modified_at", nullable = false)
    private LocalDateTime lastModifiedAt;

    @Column(name = "last_modified_by", nullable = false)
    private String lastModifiedBy;

    @OneToMany(mappedBy = "goodsReceipt", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<GoodsReceiptLineEntity> lines = new ArrayList<>();

    @Builder
    public GoodsReceiptEntity(Long id, String grnNumber, GoodsReceiptType type, GoodsReceiptStatus status, Long supplierId, Long poId, Long poTolerancePolicyId, LocalDateTime receivedAt, LocalDateTime createdAt, String createdBy, LocalDateTime lastModifiedAt, String lastModifiedBy, List<GoodsReceiptLineEntity> lines) {
        this.id = id;
        this.grnNumber = grnNumber;
        this.type = type;
        this.status = status;
        this.supplierId = supplierId;
        this.poId = poId;
        this.poTolerancePolicyId = poTolerancePolicyId;
        this.receivedAt = receivedAt;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.lastModifiedAt = lastModifiedAt;
        this.lastModifiedBy = lastModifiedBy;
        this.lines = lines;
    }
}
