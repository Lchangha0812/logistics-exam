package io.lchangha.logisticsexam.domain.inbound.inboundorder.params;

import io.lchangha.logisticsexam.domain.Quantity;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderDescription;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderTitle;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.ReferenceCode;
import io.lchangha.logisticsexam.domain.masterdata.product.ProductId;

import java.time.LocalDateTime;
import java.util.List;

public record RegistrationInfo(ReferenceCode referenceCode,
                               InboundOrderTitle title,
                               InboundOrderDescription description,
                               LocalDateTime orderRequestedAt,
                               LocalDateTime expectedArrivalAt,
                               List<RegisterItem> items
) {
    /**
     * InboundOrder에 포함될 개별 상품 정보를 담는 내부 DTO입니다.
     */
    public record RegisterItem(
            ProductId productId,
            Quantity quantity,
            Long unitPrice
    ) {
    }


}