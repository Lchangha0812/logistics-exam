package io.lchangha.logisticsexam.purchasing.app;

import io.lchangha.logisticsexam.masterdata.item.domain.Item;
import io.lchangha.logisticsexam.masterdata.item.domain.contract.ItemRepository;
import io.lchangha.logisticsexam.masterdata.item.domain.vo.Quantity;
import io.lchangha.logisticsexam.masterdata.item.domain.vo.Uom;
import io.lchangha.logisticsexam.masterdata.supplier.domain.vo.SupplierId;
import io.lchangha.logisticsexam.masterdata.vo.ItemId;
import io.lchangha.logisticsexam.purchasing.domain.PO;
import io.lchangha.logisticsexam.purchasing.domain.contract.PORepository;
import io.lchangha.logisticsexam.purchasing.infra.repo.POQueryRepository;
import io.lchangha.logisticsexam.purchasing.domain.vo.POId;
import io.lchangha.logisticsexam.purchasing.web.dto.CreatePORequest;
import io.lchangha.logisticsexam.purchasing.web.dto.POListResponse;
import io.lchangha.logisticsexam.purchasing.web.dto.POResponse;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;
import io.lchangha.logisticsexam.shared.web.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class POAppService {

    private final PORepository poRepository;
    private final POQueryRepository poQueryRepository;
    private final ItemRepository itemRepository;

    /**
     * 발주 생성 유스케이스.
     */
    public void createPO(CreatePORequest request) {
        SupplierId supplierId = new SupplierId(request.supplierId());
        PO po = PO.draft(supplierId, request.description(), request.orderDate(), request.expectedDeliveryDate());

        request.items().forEach(itemRequest -> {
            Item item = itemRepository.findBySku(itemRequest.sku())
                    .orElseThrow(() -> new NoSuchElementException("해당 SKU의 품목을 찾을 수 없습니다: " + itemRequest.sku()));
            ItemId itemId = new ItemId(item.getId());
            Quantity orderedQty = Quantity.of(itemRequest.quantity(), Uom.of(itemRequest.unit()));
            BigDecimal price = itemRequest.price();
            po.addPOLine(itemId, orderedQty, price, itemRequest.maxOverRatio());
        });

        poRepository.save(po);
    }

    /**
     * 단일 발주 조회 유스케이스.
     *
     * @param id 발주 ID
     * @return 발주 응답 DTO
     */
    public POResponse findPOResponseById(Long id) {
        return poQueryRepository.findDetailById(id)
                .orElseThrow(() -> new NoSuchElementException("발주를 찾을 수 없습니다: " + id));
    }

    /**
     * 발주 목록 조회 유스케이스.
     *
     * @param page 페이지 번호(0-base)
     * @param size 페이지 크기
     * @return 발주 응답 DTO 페이지
     */
    public PageResponse<POListResponse> findPOResponsePage(int page, int size) {
        return toListPageResponse(poQueryRepository.findOpenPage(page, size));
    }

    public void deletePOById(Long id) {
        poRepository.deleteById(new POId(id));
    }

    /**
     * 발주 승인 유스케이스.
     *
     * @param id 발주 ID
     */
    public void approvePO(Long id) {
        PO po = poRepository.findById(new POId(id))
                .orElseThrow(() -> new NoSuchElementException("발주를 찾을 수 없습니다: " + id));
        po.approve();
        poRepository.save(po);
    }

    /**
     * 발주 요청 전달(SENT 전환) 유스케이스.
     *
     * @param id 발주 ID
     */
    public void sendPO(Long id) {
        PO po = poRepository.findById(new POId(id))
                .orElseThrow(() -> new NoSuchElementException("발주를 찾을 수 없습니다: " + id));
        po.send();
        poRepository.save(po);
    }

    /**
     * 미마감(OPEN) 발주 목록 조회 유스케이스.
     *
     * @param page 페이지 번호(0-base)
     * @param size 페이지 크기
     * @return 발주 응답 DTO 페이지
     */
    public PageResponse<POListResponse> findOpenPOResponsePage(int page, int size) {
        return toListPageResponse(poQueryRepository.findOpenPage(page, size));
    }

    /**
     * 예상 납기일이 지났지만 미마감(OVERDUE) 발주 목록 조회 유스케이스.
     *
     * @param page 페이지 번호(0-base)
     * @param size 페이지 크기
     * @return 발주 응답 DTO 페이지
     */
    public PageResponse<POListResponse> findOverduePOResponsePage(int page, int size) {
        return toListPageResponse(poQueryRepository.findOverduePage(page, size));
    }

    private PageResponse<POListResponse> toListPageResponse(PageResult<POListResponse> pageResult) {
        return new PageResponse<>(pageResult.content(), pageResult.page(), pageResult.size(), pageResult.totalElements(),
                pageResult.totalPages(), pageResult.hasNext());
    }
}
