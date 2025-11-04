package io.lchangha.logisticsexam.domain.inbound.inboundorder;

import java.io.Serializable;

/**
 * InboundOrder의 참조 번호(예: 구매 주문 번호, 반품 승인 번호, 창고 이동 지시서 번호)를
 * 다형적으로 표현하기 위한 인터페이스입니다.
 * 각 구체적인 참조 번호 타입은 이 인터페이스를 구현합니다.
 */
public sealed interface ReferenceCode extends Serializable permits PurchaseOrderCode, RmaCode, TransferOrderCode {
    String value();
}
