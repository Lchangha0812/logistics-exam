package io.lchangha.logisticsexam.receiving.app;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceipt;
import io.lchangha.logisticsexam.receiving.domain.GoodsReceiptLine;
import io.lchangha.logisticsexam.receiving.domain.PoTolerancePolicy;
import io.lchangha.logisticsexam.receiving.domain.command.GoodsReceiptCreateFreeParams;
import io.lchangha.logisticsexam.receiving.domain.command.GoodsReceiptCreateFromPoParams;
import io.lchangha.logisticsexam.receiving.domain.contract.GoodsReceiptIdGenerator;
import io.lchangha.logisticsexam.receiving.domain.contract.GoodsReceiptRepository;
import io.lchangha.logisticsexam.receiving.domain.contract.PoTolerancePolicyRepository;
import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptNumber;
import io.lchangha.logisticsexam.receiving.app.mapper.GoodsReceiptCreationMapper;
import io.lchangha.logisticsexam.receiving.web.GoodsReceiptDtoMapper;
import io.lchangha.logisticsexam.receiving.web.dto.*;
import io.lchangha.logisticsexam.shared.domain.AuditInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GoodsReceiptCreationService {
    private final GoodsReceiptRepository goodsReceiptRepository;
    private final GoodsReceiptIdGenerator goodsReceiptIdGenerator;

    private final GoodsReceiptDtoMapper goodsReceiptDtoMapper;
    private final GoodsReceiptCreationMapper goodsReceiptCreationMapper;

    public CreateGoodsReceiptResponse createGoodsReceiptFromPo(CreateGoodsReceiptFromPoRequest request,
                                                               String creator) {
        Long newId = goodsReceiptIdGenerator.nextId();
        String grnNumber = Optional.ofNullable(request.grnNumber()).filter(s -> !s.isBlank()).orElseGet(goodsReceiptIdGenerator::nextNumber);
        
        List<GoodsReceiptLine> lines = goodsReceiptCreationMapper.buildPoLines(request.lines());

        GoodsReceipt receipt = GoodsReceipt.createFromPo(GoodsReceiptCreateFromPoParams.builder()
                .id(newId)
                .grnNumber(new GoodsReceiptNumber(grnNumber))
                .supplierId(request.supplierId())
                .poId(request.poId())
                .receivedAt(request.receivedAt())
                .auditInfo(AuditInfo.forCreation(creator))

                .initialLines(lines)
                .build()
        );
        goodsReceiptRepository.save(receipt);
        return new CreateGoodsReceiptResponse(receipt.getId());
    }

    public CreateGoodsReceiptResponse createFreeGoodsReceipt(CreateFreeGoodsReceiptRequest request,
                                                             String creator) {
        Long newId = goodsReceiptIdGenerator.nextId();
        String grnNumber = Optional.ofNullable(request.grnNumber()).filter(s -> !s.isBlank()).orElseGet(goodsReceiptIdGenerator::nextNumber);
        List<GoodsReceiptLine> lines = goodsReceiptCreationMapper.buildFreeLines(request.lines());

        GoodsReceipt receipt = GoodsReceipt.createFreeReceipt(GoodsReceiptCreateFreeParams.builder()
                .id(newId)
                .grnNumber(new GoodsReceiptNumber(grnNumber))
                .supplierId(request.supplierId())
                .receivedAt(request.receivedAt())
                .auditInfo(AuditInfo.forCreation(creator))
                .initialLines(lines)
                .build()
        );
        goodsReceiptRepository.save(receipt);
        return new CreateGoodsReceiptResponse(receipt.getId());
    }

    @Transactional(readOnly = true)
    public GoodsReceiptResponse getGoodsReceipt(Long grnId) {
        GoodsReceipt receipt = goodsReceiptRepository.findById(grnId)
                .orElseThrow(() -> new NoSuchElementException("GRN을 찾을 수 없습니다. id=" + grnId));
        return goodsReceiptDtoMapper.toResponse(receipt);
    }
}
