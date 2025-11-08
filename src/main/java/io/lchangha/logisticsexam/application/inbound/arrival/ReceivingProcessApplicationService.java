package io.lchangha.logisticsexam.application.inbound.arrival;

import io.lchangha.logisticsexam.application.inbound.arrival.command.ArrivalDiscrepanciesCommand;
import io.lchangha.logisticsexam.application.inbound.arrival.command.ConfirmArrivalCommand;
import io.lchangha.logisticsexam.domain.inbound.arrival.*;

import io.lchangha.logisticsexam.domain.inbound.arrival.contract.ArrivalIdGenerator;
import io.lchangha.logisticsexam.domain.inbound.arrival.contract.ArrivalRepository;
import io.lchangha.logisticsexam.domain.inbound.arrival.params.ConfirmationParam;
import io.lchangha.logisticsexam.domain.inbound.arrival.params.DiscrepancyReportParam;
import io.lchangha.logisticsexam.domain.inbound.arrival.vo.DeliveryNoteNumber;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrder;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.contract.InboundOrderRepository;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InvalidInboundOrderException;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderId;
import io.lchangha.logisticsexam.domain.masterdata.partner.vo.PartnerId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 입고 프로세스 전체를 조율하는 서비스입니다.
 * (예: 입하, 검품, 적치 등)
 */
@Service
@RequiredArgsConstructor
public class ReceivingProcessApplicationService {

    private final InboundOrderRepository inboundOrderRepository;
    private final ArrivalRepository arrivalRepository;
    private final ArrivalIdGenerator arrivalIdGenerator;

    /**
     * 입고 주문 내용과 100% 일치하는 완벽한 입하를 확정하는 유스케이스입니다.
     * 이 메서드는 단일 트랜잭션으로 실행되어야 합니다.
     *
     * @param command 입하 확정 처리를 위한 데이터 (입고 주문 ID, 납품서 사진 키, 공급업체 ID)
     */
    public void confirmArrival(ConfirmArrivalCommand command) {
        InboundOrder inboundOrder = inboundOrderRepository.findById(InboundOrderId.of(command.inboundOrderId()))
                .orElseThrow(() -> new InvalidInboundOrderException("입고 주문을 찾을 수 없습니다."));

        ConfirmationParam params = ConfirmationParam.builder()
                .inboundOrder(inboundOrder)
                .supplierId(PartnerId.of(command.supplierId()))
                .deliveryNoteNumber(DeliveryNoteNumber.of(command.deliveryNotePhotoKey()))
                .build();

        Arrival newArrival = Arrival.confirmedFrom(params, arrivalIdGenerator);
        arrivalRepository.save(newArrival);
    }

    /**
     * 입고 시 차이(수량, 파손 등)가 발생한 경우, 이를 기록하며 입하를 처리하는 유스케이스입니다.
     * 이 메서드는 단일 트랜잭션으로 실행되어야 합니다.
     *
     * @param command 입하 및 클레임 처리를 위한 데이터
     */
    public void reportDiscrepancies(ArrivalDiscrepanciesCommand command) {
        InboundOrder inboundOrder = inboundOrderRepository.findById(new InboundOrderId(command.inboundOrderId()))
                .orElseThrow(() -> new InvalidInboundOrderException("입고 주문을 찾을 수 없습니다."));

        var params = DiscrepancyReportParam.builder()
                .inboundOrder(inboundOrder)
                .supplierId(PartnerId.of(command.supplierId()))
                .deliveryNoteNumber(DeliveryNoteNumber.of(command.deliveryNotePhotoKey()))
                .actualArrivalTime(command.actualArrivalTime())
                .claims(command.claims())
                .build();

        Arrival newArrival = Arrival.reportedWithDiscrepancies(params, arrivalIdGenerator);

        arrivalRepository.save(newArrival);
    }
}
