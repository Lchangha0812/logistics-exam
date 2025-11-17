package io.lchangha.logisticsexam.receiving.app.query;

import io.lchangha.logisticsexam.receiving.web.dto.ReceivingVarianceReportRequest;
import io.lchangha.logisticsexam.receiving.web.dto.ReceivingVarianceReportResponse;
import io.lchangha.logisticsexam.receiving.web.dto.ReceivingVarianceRowResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceivingVarianceQueryService {
    private final ReceivingVarianceQueryRepository receivingVarianceQueryRepository;

    @Transactional(readOnly = true)
    public ReceivingVarianceReportResponse getVarianceReport(ReceivingVarianceReportRequest request) {
        ReceivingVarianceFilter filter = new ReceivingVarianceFilter(
                request.fromDate(),
                request.toDate(),
                request.supplierId(),
                request.itemId(),
                request.warehouseId()
        );
        List<ReceivingVarianceRowResponse> rows = receivingVarianceQueryRepository.findVariance(filter).stream()
                .map(row -> new ReceivingVarianceRowResponse(
                        row.supplierId(),
                        row.supplierName(),
                        row.itemId(),
                        row.itemName(),
                        row.warehouseId(),
                        row.overQty(),
                        row.underQty(),
                        row.extraQty(),
                        row.substituteQty()
                ))
                .toList();
        return new ReceivingVarianceReportResponse(rows);
    }
}
