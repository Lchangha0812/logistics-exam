package io.lchangha.logisticsexam.application.inbound.inboundorder;

import io.lchangha.logisticsexam.application.inbound.contract.InboundOrderIdGenerator;
import io.lchangha.logisticsexam.application.inbound.inboundorder.command.RegisterInboundOrderCommand;
import io.lchangha.logisticsexam.application.inbound.inboundorder.mapper.InboundOrderMapper;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrder;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderItemId;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.InboundOrderRepository;
import io.lchangha.logisticsexam.domain.inbound.inboundorder.params.RegistrationInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
        var registrationInfo = mapper.toRegistrationInfo(command);
        var newOrderId = inboundOrderIdGenerator.nextOrderId();
        List<InboundOrderItemId> newItemIds = registrationInfo.items().stream()
                .map(item -> inboundOrderIdGenerator.nextItemId())
                .toList();

        InboundOrder inboundOrder = InboundOrder.register(newOrderId, newItemIds, registrationInfo);
        repository.save(inboundOrder);
    }

}
