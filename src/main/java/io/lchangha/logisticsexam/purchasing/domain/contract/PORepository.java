package io.lchangha.logisticsexam.purchasing.domain.contract;

import io.lchangha.logisticsexam.purchasing.domain.PO;
import io.lchangha.logisticsexam.purchasing.domain.vo.POId;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;

import java.util.Optional;

public interface PORepository {
    void save(PO po);

    Optional<PO> findById(POId poId);

    void deleteById(POId poId);
}
