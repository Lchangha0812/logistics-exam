package io.lchangha.logisticsexam.receiving.domain.vo;

/**
 * PO 발주 수량 대비 입고 수량의 불일치를 표시하는 레코드입니다.
 *
 * @param code 차이 발생 원인 (과입고, 과소입고 등)
 * @param note 입고 담당자가 입력한 자유 형식의 메모
 */
public record ReceiptLineDiscrepancy(
        DiscrepancyCode code,
        String note
) {
    public ReceiptLineDiscrepancy {
        if (note != null && note.isBlank()) {
            note = null;
        }
    }
}
