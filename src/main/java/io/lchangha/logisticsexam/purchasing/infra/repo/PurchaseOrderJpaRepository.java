package io.lchangha.logisticsexam.purchasing.infra.repo;

import io.lchangha.logisticsexam.purchasing.infra.entity.POEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderJpaRepository extends JpaRepository<POEntity, Long> {
}
