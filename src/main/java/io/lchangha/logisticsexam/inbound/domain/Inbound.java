package io.lchangha.logisticsexam.inbound.domain;

import io.lchangha.logisticsexam.id.InboundId;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@code Inbound} 애그리거트 루트는 창고로 들어오는 상품의 입고 과정을 관리합니다.
 * 입고 예정부터 실제 입고 처리까지의 전체 흐름을 나타내며, 여러 {@link InboundItem}을 포함하는 컨테이너 역할을 합니다.
 *
 * 이 애그리거트는 다음과 같은 핵심 역할을 수행합니다:
 * <ul>
 *     <li>**입고 식별:** {@link InboundId}를 통해 시스템 내에서 고유하게 식별됩니다.</li>
 *     <li>**입고 정보:** 입고 제목, 설명, 요청일, 예상 도착일 등 입고의 기본적인 정보를 정의합니다.</li>
 *     <li>**입고 상품 관리:** {@link InboundItem} 목록을 관리하며, 각 상품별 입고 수량 및 단가 정보를 포함합니다.</li>
 *     <li>**입고 상태 관리:** {@link InboundStatus}를 통해 입고의 현재 진행 상태(예: 요청됨, 확정됨, 거절됨)를 추적합니다.</li>
 *     <li>**데이터 일관성 보장:** 입고와 관련된 모든 비즈니스 규칙 및 상태 전이 로직은 이 애그리거트 내에서 관리되어 데이터의 일관성을 보장합니다.</li>
 * </ul>
 *
 * {@code Inbound}는 WMS의 핵심 프로세스 중 하나인 입고 관리의 시작점이며,
 * {@link InboundItem}은 {@code Inbound} 애그리거트의 일부로서 {@code Inbound}의 생명 주기에 종속됩니다.
 */
@Getter
@ToString
@EqualsAndHashCode(of = "id")
public class Inbound {
    private InboundId id;
    private final String title;
    private final String description;
    private final LocalDateTime orderRequestedAt;
    private final LocalDateTime estimatedArrivalAt;
    private final List<InboundItem> inboundItems = new ArrayList<>();
    private InboundStatus status;

    @Builder(access = AccessLevel.PACKAGE)
    private Inbound(
            InboundId id,
            String title,
            String description,
            LocalDateTime orderRequestedAt,
            LocalDateTime estimatedArrivalAt,
            List<InboundItem> inboundItems) {
        validate(title, description, orderRequestedAt, estimatedArrivalAt, inboundItems);

        this.id = id;
        this.title = title;
        this.description = description;
        this.orderRequestedAt = orderRequestedAt;
        this.estimatedArrivalAt = estimatedArrivalAt;
        this.status = InboundStatus.REQUESTED;
        inboundItems.forEach(this::addInboundItem);
    }

    private void validate(String title, String description, LocalDateTime orderRequestedAt, LocalDateTime estimatedArrivalAt, List<InboundItem> inboundItems) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("입고 제목은 필수입니다.");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("입고 설명은 필수입니다.");
        }
        Objects.requireNonNull(orderRequestedAt, "입고 요청일은 필수입니다.");
        Objects.requireNonNull(estimatedArrivalAt, "입고 예정일은 필수입니다.");
        if (inboundItems == null || inboundItems.isEmpty()) {
            throw new IllegalArgumentException("입고 상품은 필수입니다.");
        }
    }

    private void addInboundItem(InboundItem inboundItem) {
        inboundItems.add(inboundItem);
        inboundItem.assignInbound(this);
    }
}
