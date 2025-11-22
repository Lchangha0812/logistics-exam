package io.lchangha.logisticsexam.purchasing.infra.repo;

import io.lchangha.logisticsexam.purchasing.web.dto.POListResponse;
import io.lchangha.logisticsexam.purchasing.web.dto.POResponse;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;

import java.util.Optional;

public interface POQueryRepository {
    PageResult<POListResponse> findOpenPage(int page, int size);

    PageResult<POListResponse> findOverduePage(int page, int size);

    Optional<POResponse> findDetailById(Long id);
}
