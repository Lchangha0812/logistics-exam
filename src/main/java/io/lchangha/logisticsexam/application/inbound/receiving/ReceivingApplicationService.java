package io.lchangha.logisticsexam.application.inbound.receiving;


import io.lchangha.logisticsexam.application.inbound.receiving.command.RecordReceivingCommand;
import io.lchangha.logisticsexam.domain.inbound.arrival.Arrival;
import io.lchangha.logisticsexam.domain.inbound.arrival.contract.ArrivalRepository;
import io.lchangha.logisticsexam.domain.inbound.receiving.Receiving;
import io.lchangha.logisticsexam.domain.inbound.receiving.contract.ReceivingIdGenerator;
import io.lchangha.logisticsexam.domain.inbound.receiving.contract.ReceivingRepository;
import io.lchangha.logisticsexam.domain.inbound.receiving.params.PutawayDetail;
import io.lchangha.logisticsexam.domain.inbound.receiving.params.RecordReceivingParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * 입고(Receiving) 및 적치(Put-away) 프로세스를 관리하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class ReceivingApplicationService {

    private final ReceivingRepository receivingRepository;
    private final ReceivingIdGenerator receivingIdGenerator;
    private final ArrivalRepository arrivalRepository;

    /**
     * 도착한 상품을 지정된 위치에 적치하고, 이를 시스템에 기록하는 유스케이스입니다.
     * 이 메서드는 단일 트랜잭션으로 실행되어야 합니다.
     *
     * @param command 적치 기록을 위한 데이터 (도착 ID, 적치 상세 내역)
     */
    @Transactional
    public void recordReceiving(RecordReceivingCommand command) {
        // 1. Arrival 애그리거트를 조회한다.
        Arrival arrival = arrivalRepository.findById(command.arrivalId())
                .orElseThrow(() -> new IllegalArgumentException("해당 입하 정보를 찾을 수 없습니다. ID: " + command.arrivalId().value()));

        // 2. 커맨드의 위치 지정 정보를 조회하기 쉬운 Map 형태로 변환한다.
        Map<ArrivalItemId, LocationId> locationMap = command.receivingDetailCommands().stream()
                .collect(Collectors.toMap(ReceivingDetailCommand::arrivalItemId, ReceivingDetailCommand::locationId));

        // 3. 도메인에 전달할 데이터(ItemToReceive) 목록을 생성한다.
        // Arrival 정보와 위치 정보를 조합한다.
        var itemsToReceive = arrival.getArrivalItems().stream()
                .map(item -> {
                    LocationId locationId = locationMap.get(item.getId());
                    if (locationId == null) {
                        throw new IllegalArgumentException("도착 품목 ID %d에 대한 위치가 지정되지 않았습니다.".formatted(item.getId().value()));
                    }
                    return new ItemToReceive(
                            item.getProductId(),
                            item.getArrivedQuantity(),
                            item.getLotNumber(),
                            locationId
                    );
                })
                .collect(Collectors.toList());

        // 4. 도메인 파라미터를 생성한다.
        var params = new RecordReceivingParam(
                arrival.getId(),
                LocalDateTime.now(),
                itemsToReceive
        );

        // 5. 도메인 로직(팩토리 메서드)을 호출하여 애그리거트를 생성한다.
        Receiving newReceiving = Receiving.record(params, receivingIdGenerator);

        // 6. 생성된 애그리거트를 저장한다.
        receivingRepository.save(newReceiving);

        // TODO: 재고(Stock) 업데이트 이벤트 발행
    }
}
