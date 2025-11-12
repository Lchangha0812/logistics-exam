package io.lchangha.logisticsexam.masterdata.domain.contract;

import io.lchangha.logisticsexam.masterdata.domain.Item;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;

import java.util.Optional;

public interface ItemRepository {
    Item save(Item item);

    Optional<Item> findById(Long id);

    PageResult<Item> findPage(int page, int size);
}