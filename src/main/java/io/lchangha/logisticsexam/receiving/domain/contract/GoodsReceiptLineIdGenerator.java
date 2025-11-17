package io.lchangha.logisticsexam.receiving.domain.contract;

import io.lchangha.logisticsexam.receiving.domain.vo.GoodsReceiptLineId;

/**
 * 입고 라인(GoodsReceiptLine)의 ID를 생성하는 책임을 정의한 인터페이스입니다.
 */
public interface GoodsReceiptLineIdGenerator {
    /**
     * 다음 입고 라인 ID를 생성합니다.
     *
     * @return 새로운 입고 라인 ID
     */
    GoodsReceiptLineId nextId();
}
