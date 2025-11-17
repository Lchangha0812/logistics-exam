package io.lchangha.logisticsexam.receiving.infra.repo;

import io.lchangha.logisticsexam.receiving.infra.repo.entity.PoTolerancePolicyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaPoTolerancePolicyRepository extends JpaRepository<PoTolerancePolicyEntity, Long> {
}
