package io.lchangha.logisticsexam.receiving.web;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceipt;
import io.lchangha.logisticsexam.receiving.domain.GoodsReceiptLine;
import io.lchangha.logisticsexam.receiving.domain.vo.LinePoLink;
import io.lchangha.logisticsexam.receiving.domain.vo.LotInfo;
import io.lchangha.logisticsexam.receiving.domain.vo.ReceiptLineDiscrepancy;
import io.lchangha.logisticsexam.receiving.web.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface GoodsReceiptDtoMapper {

    @Mappings({
            @Mapping(source = "grnNumber.value", target = "grnNumber"),
            @Mapping(source = "lines", target = "lines")
    })
    GoodsReceiptResponse toResponse(GoodsReceipt receipt);

    @Mappings({
            @Mapping(source = "id.value", target = "lineId"),
            @Mapping(source = "poLinks", target = "poLinks"),
            @Mapping(source = "lotInfo", target = "lotInfo"),
            @Mapping(source = "discrepancy", target = "discrepancy")
    })
    GoodsReceiptLineResponse toLineResponse(GoodsReceiptLine line);

    default LotInfoPayload map(LotInfo lotInfo) {
        if (lotInfo == null) {
            return null;
        }
        return new LotInfoPayload(lotInfo.lotCode(), lotInfo.expiryDate());
    }

    default LinePoLinkResponse map(LinePoLink link) {
        if (link == null) {
            return null;
        }
        return new LinePoLinkResponse(link.poLineId(), link.quantity());
    }

    default DiscrepancyResponse map(ReceiptLineDiscrepancy discrepancy) {
        if (discrepancy == null) {
            return null;
        }
        return new DiscrepancyResponse(discrepancy.code(), discrepancy.note());
    }
}
