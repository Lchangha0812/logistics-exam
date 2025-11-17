package io.lchangha.logisticsexam.receiving.domain.contract;

import io.lchangha.logisticsexam.receiving.domain.PoTolerancePolicy;

import java.util.Optional;

/**
 * PO 허용 오차 정책(PoTolerancePolicy)의 데이터 영속성을 처리하는 리포지토리 인터페이스입니다.
 */
public interface PoTolerancePolicyRepository {
    /**
     * 특정 PO에 적용되는 정책을 조회합니다.
     *
     * @param poId PO ID
     * @return 조회된 정책 (Optional)
     */
    Optional<PoTolerancePolicy> findForPo(Long poId);

    /**
     * 시스템의 기본 허용 오차 정책을 조회합니다.
     *
     * @return 기본 정책 (Optional)
     */
    Optional<PoTolerancePolicy> findDefaultPolicy();

    /**
     * ID를 기준으로 PO 허용 오차 정책을 조회합니다.
     *
     * @param id 조회할 정책 ID
     * @return 조회된 정책 (Optional)
     */
    Optional<PoTolerancePolicy> findById(Long id);
}
