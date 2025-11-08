package io.lchangha.logisticsexam.domain.inbound.inboundorder.params;


import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderDescription;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderTitle;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.ReferenceCode;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record RegistrationParam(ReferenceCode referenceCode,
                                InboundOrderTitle title,
                                InboundOrderDescription description,
                                LocalDateTime orderRequestedAt,
                                LocalDateTime expectedArrivalAt,
                                List<RegisterItemParam> items
) {
}