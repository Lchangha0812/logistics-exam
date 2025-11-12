package io.lchangha.logisticsexam.masterdata.domain.contract;

import io.lchangha.logisticsexam.masterdata.domain.Supplier;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;

import java.util.List;
import java.util.Optional;

public interface SupplierRepository {
    Supplier save(Supplier supplier);

    Optional<Supplier> findById(Long id);

    PageResult<Supplier> findPage(int page, int size);
}
