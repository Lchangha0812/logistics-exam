package io.lchangha.logisticsexam.application.inbound.receiving;


import io.lchangha.logisticsexam.domain.WarehouseId;
import io.lchangha.logisticsexam.domain.inbound.arrival.Arrival;
import io.lchangha.logisticsexam.domain.inbound.arrival.vo.ArrivalId;
import io.lchangha.logisticsexam.domain.inbound.arrival.contract.ArrivalRepository;
import io.lchangha.logisticsexam.domain.inbound.receiving.InvalidReceivingException;
import io.lchangha.logisticsexam.domain.inbound.receiving.Receiving;
import io.lchangha.logisticsexam.domain.inbound.receiving.contract.ReceivingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 입고(Receiving) 및 적치(Put-away) 프로세스를 관리하는 서비스입니다.
 */
@Service
@RequiredArgsConstructor
public class ReceivingApplicationService {

    private final ArrivalRepository arrivalRepository;
    private final ReceivingRepository receivingRepository;

    /**
     * 도착한 상품을 지정된 위치에 적치하고, 이를 시스템에 기록하는 유스케이스입니다.
     * 이 메서드는 단일 트랜잭션으로 실행되어야 합니다.
     *
     * @param command 적치 기록을 위한 데이터 (도착 ID, 적치 상세 내역)
     */
    @Transactional
    public void recordPutaway(RecordPutawayCommand command) {
        // 1. 엔티티 접근
        Arrival arrival = arrivalRepository.findById(new ArrivalId(command.arrivalId()))
                .orElseThrow(() -> new InvalidReceivingException("도착 정보를 찾을 수 없습니다."));

        // 2. 엔티티 메서드 호출 (도메인 로직 실행)
        var params = new Receiving.PutawayParams(
                arrival,
                new WarehouseId(command.warehouseId()),
                command.putawayDetails()
        );

        Receiving newReceiving = Receiving.putawayFrom(
                params,
                receivingIdGenerator,
                receivingItemIdGenerator
        );

        // 3. 엔티티 저장
        receivingRepository.save(newReceiving);

        // TODO: [중요] 재고(Stock) 업데이트.
        // `ReceivingCompletedEvent`와 같은 도메인 이벤트를 발행하여, Stock을 관리하는 다른 서비스가 재고를 증가시키도록 처리해야 합니다.
        // 이벤트에는 어떤 상품(productId, lotNumber)이 어느 위치(locationId)에 얼마나(quantity) 추가되었는지 정보가 포함되어야 합니다.
    }
}
