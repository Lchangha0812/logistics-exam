package io.lchangha.logisticsexam.receiving.infra.repo;

import io.lchangha.logisticsexam.receiving.infra.repo.entity.GoodsReceiptEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaGoodsReceiptRepository extends JpaRepository<GoodsReceiptEntity, Long> {
}
