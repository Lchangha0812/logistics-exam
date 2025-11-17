package io.lchangha.logisticsexam.receiving.infra.repo;

import io.lchangha.logisticsexam.receiving.infra.repo.entity.GoodsReceiptLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGoodsReceiptLineRepository extends JpaRepository<GoodsReceiptLineEntity, Long> {
}
