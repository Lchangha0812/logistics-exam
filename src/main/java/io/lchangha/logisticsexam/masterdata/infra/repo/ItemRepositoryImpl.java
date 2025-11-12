package io.lchangha.logisticsexam.masterdata.infra.repo;

import io.lchangha.logisticsexam.masterdata.domain.Item;
import io.lchangha.logisticsexam.masterdata.domain.contract.ItemRepository;
import io.lchangha.logisticsexam.masterdata.infra.entity.ItemEntity;
import io.lchangha.logisticsexam.masterdata.infra.mapper.ItemEntityMapper;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Primary
@RequiredArgsConstructor
public class ItemRepositoryImpl implements ItemRepository {

    private final JpaItemRepository jpaRepository;
    private final ItemEntityMapper mapper;

    @Override
    public Item save(Item item) {
        ItemEntity entity = mapper.toEntity(item);
        ItemEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Item> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageResult<Item> findPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ItemEntity> pages = jpaRepository.findAll(pageable);
        List<Item> list = pages.getContent().stream().map(mapper::toDomain).toList();
        return new PageResult<>(list, page, size, pages.getTotalElements(), pages.getTotalPages(), pages.hasNext());
    }
}
