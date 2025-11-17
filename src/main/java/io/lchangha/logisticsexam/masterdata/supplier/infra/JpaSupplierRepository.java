package io.lchangha.logisticsexam.masterdata.supplier.infra;

import io.lchangha.logisticsexam.masterdata.supplier.infra.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSupplierRepository extends JpaRepository<SupplierEntity, Long> {
}
