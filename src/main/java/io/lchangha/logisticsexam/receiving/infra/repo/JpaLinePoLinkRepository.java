package io.lchangha.logisticsexam.receiving.infra.repo;

import io.lchangha.logisticsexam.receiving.infra.repo.entity.LinePoLinkEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaLinePoLinkRepository extends JpaRepository<LinePoLinkEntity, Long> {
}
