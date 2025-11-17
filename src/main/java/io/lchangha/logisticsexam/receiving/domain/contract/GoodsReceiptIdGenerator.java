package io.lchangha.logisticsexam.receiving.domain.contract;

/**
 * 입고(GoodsReceipt) 애그리거트의 ID와 문서 번호를 생성하는 책임을 정의한 인터페이스입니다.
 */
public interface GoodsReceiptIdGenerator {
    /**
     * 다음 입고 ID (PK)를 생성합니다.
     *
     * @return 새로운 입고 ID
     */
    Long nextId();

    /**
     * 다음 입고 문서 번호(GRN)를 생성합니다.
     *
     * @return 새로운 입고 문서 번호
     */
    String nextNumber();
}
