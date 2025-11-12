package io.lchangha.logisticsexam.masterdata.infra.repo;

import io.lchangha.logisticsexam.masterdata.infra.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSupplierRepository extends JpaRepository<SupplierEntity, Long> {
}
