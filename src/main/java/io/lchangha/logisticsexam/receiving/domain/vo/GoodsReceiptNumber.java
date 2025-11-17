package io.lchangha.logisticsexam.receiving.domain.vo;

import org.springframework.util.Assert;

/**
 * 사람이 읽고 검색하기 쉬운 입고 문서 번호를 나타내는 레코드입니다.
 *
 * @param value 입고 문서 번호 값
 */
public record GoodsReceiptNumber(String value) {
    public GoodsReceiptNumber {
        Assert.hasText(value, "입고 번호는 비어 있을 수 없습니다.");
    }
}
