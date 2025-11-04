package io.lchangha.logisticsexam.application.inbound;

import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderId;

public class PrepareReceivingApplicationService {
    // 이건 도메인 서비스일 확률이 굉장히 높음
    public void planPutawayLocations(InboundOrderId inboundOrderId) {
        // TODO: [사전준비] 최적 보관 위치 선정 (Put-away Planning) 유스케이스 구현 필요.
    }

    public void planLaborAndEquipment(InboundOrderId inboundOrderId) {
        // TODO: [사전준비] 작업자 및 장비 배치 (Labor Planning) 유스케이스 구현 필요.
    }

    public void identifyCrossDockingOpportunities(InboundOrderId inboundOrderId) {
        // TODO: [사전준비] 크로스도킹 기회 식별 (Cross-Docking) 유스케이스 구현 필요.
    }
}
