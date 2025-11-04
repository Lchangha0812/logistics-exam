package io.lchangha.logisticsexam.domain.inbound.receiving;

import io.lchangha.logisticsexam.domain.DomainValidator;
import io.lchangha.logisticsexam.domain.WarehouseId;
import io.lchangha.logisticsexam.domain.inbound.arrival.Arrival;
import io.lchangha.logisticsexam.domain.inbound.arrival.ArrivalId;
import io.lchangha.logisticsexam.domain.inbound.exception.InvalidReceivingException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * {@code Receiving} 애그리거트 루트는 WMS(창고 관리 시스템)에서 상품의 최종 입고 및 적치 과정을 나타냅니다.
 * 이는 {@link Arrival}을 통해 도착한 상품이 실제 창고의 특정 로케이션에 할당되고 재고로 반영되는 흐름을 관리합니다.
 *
 * 이 애그리거트는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**입고 식별:** {@link ReceivingId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**입하 참조:** {@link ArrivalId}를 참조하여 어떤 입하에 대한 입고인지 연결됩니다.</li>
 *     <li>**입고 정보:** 실제 입고 날짜({@code receivingDate}), 입고가 이루어진 창고({@link WarehouseId}) 등 입고 관련 상세 정보를 포함합니다.</li>
 *     <li>**입고 상품 관리:** {@link ReceivingItem} 목록을 관리하여 어떤 상품이 얼마나 입고되었고, 어느 로케이션에 적치되었는지 상세히 기록합니다.</li>
 *     <li>**상태 관리:** {@link ReceivingStatus}를 통해 입고의 현재 상태(예: 적치 대기, 적치 중, 적치 완료 등)를 추적합니다.</li>
 * </ul>
 *
 * {@code Receiving}은 {@code Arrival} 이후의 단계로, 상품의 물리적 흐름을 완료하고 재고 시스템에 반영하는 최종 단계를 책임집니다.
 * {@link ReceivingItem}은 {@code Receiving} 애그리거트의 일부로서 {@code Receiving}의 생명 주기에 종속됩니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Receiving {
    private ReceivingId id;
    private final ArrivalId arrivalId;
    private final LocalDateTime receivingDate;
    private final WarehouseId warehouseId;
    private ReceivingStatus status;
    private final List<ReceivingItem> receivingItems = new ArrayList<>();

    @Builder(access = AccessLevel.PACKAGE)
    private Receiving(
            ReceivingId id,
            ArrivalId arrivalId,
            LocalDateTime receivingDate,
            WarehouseId warehouseId,
            List<ReceivingItem> receivingItems) {
        this.id = id;
        this.arrivalId = DomainValidator.requireNonNull(arrivalId, () -> new InvalidReceivingException("입하 ID는 필수입니다."));
        this.receivingDate = DomainValidator.requireNonNull(receivingDate, () -> new InvalidReceivingException("입고 날짜는 필수입니다."));
        this.warehouseId = DomainValidator.requireNonNull(warehouseId, () -> new InvalidReceivingException("창고 ID는 필수입니다."));
        DomainValidator.requireNonEmpty(receivingItems, () -> new InvalidReceivingException("입고 상품은 필수입니다."));
        receivingItems.forEach(this::addReceivingItem);
        this.status = ReceivingStatus.PENDING_PUTAWAY; // 초기 상태는 적치 대기
    }

    public List<ReceivingItem> getReceivingItems() {
        return Collections.unmodifiableList(receivingItems);
    }

    private void addReceivingItem(ReceivingItem item) {
        this.receivingItems.add(item);
        item.assignReceiving(this);
    }

    /**
     * 모든 상품의 적치가 완료되었음을 시스템에 기록합니다.
     *
     * @throws InvalidReceivingException 적치 대기 상태가 아닐 경우
     */
    public void completePutAway() {
        if (status != ReceivingStatus.PENDING_PUTAWAY) {
            throw new InvalidReceivingException("적치 대기 상태에서만 적치를 완료할 수 있습니다.");
        }
        status = ReceivingStatus.PUTAWAY_COMPLETED;
    }

    /**
     * 입고(적치)를 거절 처리합니다.
     *
     * @throws InvalidReceivingException 이미 적치 완료된 경우
     */
    public void reject() {
        if (status == ReceivingStatus.PUTAWAY_COMPLETED) {
            throw new InvalidReceivingException("이미 적치 완료된 입고는 거절할 수 없습니다.");
        }
        status = ReceivingStatus.REJECTED;
    }
}
