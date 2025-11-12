package io.lchangha.logisticsexam.masterdata.infra.repo;

import io.lchangha.logisticsexam.masterdata.infra.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItemRepository extends JpaRepository<ItemEntity, Long> {
}
