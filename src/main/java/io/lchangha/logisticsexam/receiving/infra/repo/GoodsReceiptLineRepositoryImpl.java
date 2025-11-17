package io.lchangha.logisticsexam.receiving.infra.repo;

import io.lchangha.logisticsexam.receiving.domain.contract.GoodsReceiptLineRepository;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsReceiptLineRepositoryImpl implements GoodsReceiptLineRepository {
    // 현재는 애그리거트 루트를 통해서만 라인이 관리되므로 별도 구현 없음
}