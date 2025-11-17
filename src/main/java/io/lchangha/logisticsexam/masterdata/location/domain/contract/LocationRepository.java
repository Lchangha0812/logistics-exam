package io.lchangha.logisticsexam.masterdata.location.domain.contract;

import io.lchangha.logisticsexam.masterdata.location.domain.Location;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;

import java.util.Optional;

public interface LocationRepository {
    Location save(Location location);

    Optional<Location> findById(Long id);

    PageResult<Location> findPage(int page, int size);
}