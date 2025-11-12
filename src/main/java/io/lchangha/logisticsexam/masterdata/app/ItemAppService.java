package io.lchangha.logisticsexam.masterdata.app;

import io.lchangha.logisticsexam.masterdata.domain.Item;
import io.lchangha.logisticsexam.masterdata.domain.contract.ItemIdGenerator;
import io.lchangha.logisticsexam.masterdata.domain.contract.ItemRepository;
import io.lchangha.logisticsexam.masterdata.web.dto.CreateItemRequest;
import io.lchangha.logisticsexam.masterdata.web.dto.ItemResponse;
import io.lchangha.logisticsexam.masterdata.web.dto.UpdateItemRequest;
import io.lchangha.logisticsexam.masterdata.web.mapper.ItemDtoMapper;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;
import io.lchangha.logisticsexam.shared.web.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemAppService {
    private final ItemRepository itemRepository;
    private final ItemIdGenerator itemIdGenerator;
    private final ItemDtoMapper itemDtoMapper;

    /**
     * 새로운 아이템을 생성합니다.
     * @param request 생성 요청 DTO
     * @param creator 생성자 ID
     */
    public void createItem(CreateItemRequest request, String creator) {
        Long newId = itemIdGenerator.next();
        Item item = itemDtoMapper.toDomain(request, newId, creator);
        itemRepository.save(item);
    }

    /**
     * ID로 아이템 정보를 조회합니다.
     * @param id 아이템 ID
     * @return 조회된 아이템 응답 DTO
     * @throws NoSuchElementException 해당 ID의 아이템을 찾을 수 없을 경우
     */
    @Transactional(readOnly = true)
    public ItemResponse getItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("ID가 " + id + "인 아이템을 찾을 수 없습니다."));
        return itemDtoMapper.toResponse(item);
    }

    /**
     * 아이템 정보를 업데이트합니다.
     * @param request 업데이트 요청 DTO
     * @param updater 수정자 ID
     * @throws NoSuchElementException 해당 ID의 아이템을 찾을 수 없을 경우
     */
    public void updateItem(UpdateItemRequest request, String updater) {
        Item existingItem = itemRepository.findById(request.id())
                .orElseThrow(() -> new NoSuchElementException("ID가 " + request.id() + "인 아이템을 찾을 수 없습니다."));

        Item updatedItem = itemDtoMapper.toDomain(request, existingItem, updater);
        itemRepository.save(updatedItem);
    }

    /**
     * 페이징 처리된 아이템 목록을 조회합니다.
     * @param page 조회할 페이지 번호
     * @param size 페이지당 아이템 수
     * @return 페이징 처리된 아이템 응답 DTO 목록
     */
    @Transactional(readOnly = true)
    public PageResponse<ItemResponse> getItems(int page, int size) {
        PageResult<Item> items = itemRepository.findPage(page, size);
        var response = items.content().stream().map(itemDtoMapper::toResponse).toList();
        return new PageResponse<>(response, items.page(), items.size(), items.totalElements(), items.totalPages(), items.hasNext());
    }
}
