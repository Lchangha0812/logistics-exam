package io.lchangha.logisticsexam.masterdata.item.infra;

import io.lchangha.logisticsexam.masterdata.item.infra.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItemRepository extends JpaRepository<ItemEntity, Long> {
}
