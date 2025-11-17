package io.lchangha.logisticsexam.receiving.app.query;

import java.util.List;

public interface ReceivingVarianceQueryRepository {
    List<ReceivingVarianceRow> findVariance(ReceivingVarianceFilter filter);
}
