package io.lchangha.logisticsexam.application.inbound.inboundorder;

import io.lchangha.logisticsexam.domain.inbound.inboundorder.contract.InboundOrderIdGenerator;
import io.lchangha.logisticsexam.application.inbound.inboundorder.command.RecordPutawayLocationsCommand;
import io.lchangha.logisticsexam.application.inbound.inboundorder.command.RegisterInboundOrderCommand;
import io.lchangha.logisticsexam.application.inbound.inboundorder.mapper.InboundOrderMapper;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrder;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.contract.InboundOrderRepository;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.vo.InboundOrderId;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;



/**
 * InboundOrder와 관련된 유스케이스를 처리하는 애플리케이션 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class InboundOrderApplicationService {
    private final InboundOrderRepository repository;
    private final InboundOrderIdGenerator inboundOrderIdGenerator;
    private final InboundOrderMapper mapper;


    /**
     * 외부 시스템이나 사용자 입력으로부터 받은 정보로 새로운 InboundOrder를 등록합니다.
     *
     * @param command InboundOrder 생성을 위한 데이터
     */
    public void register(RegisterInboundOrderCommand command) {
        var params = mapper.toRegistrationParam(command);
        InboundOrder inboundOrder = InboundOrder.register(params, inboundOrderIdGenerator);
        repository.save(inboundOrder);
    }

    /**
     * [사전준비] 입고 지시 ID를 기반으로 보관 위치를 계획합니다.
     *
     * @param command 보관 위치 기록을 위한 데이터
     */
    public void planPutawayLocations(RecordPutawayLocationsCommand command) {
        InboundOrder inboundOrder = repository.findById(command.inboundOrderId())
                .orElseThrow(() -> new IllegalArgumentException("해당 입고 지시를 찾을 수 없습니다. ID: " + command.inboundOrderId().value()));

        // TODO: InboundOrder 도메인 객체에 보관 위치 기록 로직 추가 필요
        // inboundOrder.recordPutawayLocations(command.putawayDetails());

        repository.save(inboundOrder);
    }
}
