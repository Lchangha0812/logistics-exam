package io.lchangha.logisticsexam.receiving.infra.repo;

import io.lchangha.logisticsexam.receiving.domain.PoTolerancePolicy;
import io.lchangha.logisticsexam.receiving.domain.contract.PoTolerancePolicyRepository;
import io.lchangha.logisticsexam.receiving.infra.mapper.PoTolerancePolicyMapper;
import io.lchangha.logisticsexam.receiving.infra.repo.entity.PoTolerancePolicyEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class PoTolerancePolicyRepositoryImpl implements PoTolerancePolicyRepository {

    private final JpaPoTolerancePolicyRepository jpaPoTolerancePolicyRepository;
    private final PoTolerancePolicyMapper poTolerancePolicyMapper;

    public PoTolerancePolicyRepositoryImpl(JpaPoTolerancePolicyRepository jpaPoTolerancePolicyRepository, PoTolerancePolicyMapper poTolerancePolicyMapper) {
        this.jpaPoTolerancePolicyRepository = jpaPoTolerancePolicyRepository;
        this.poTolerancePolicyMapper = poTolerancePolicyMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PoTolerancePolicy> findForPo(Long poId) {
        // TODO: PO별 정책 조회 로직 구현 필요 (현재는 PO ID로 직접 조회하는 로직이 없음)
        // 임시로 기본 정책을 반환하거나, PO ID와 매핑되는 정책을 찾아야 함
        return findDefaultPolicy(); // 임시 구현
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PoTolerancePolicy> findDefaultPolicy() {
        // 실제 DB에서 기본 정책을 조회하는 로직이 필요.
        // 현재는 ID가 0L인 정책을 기본 정책으로 가정하고 조회
        return jpaPoTolerancePolicyRepository.findById(0L)
                .map(poTolerancePolicyMapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PoTolerancePolicy> findById(Long id) {
        return jpaPoTolerancePolicyRepository.findById(id)
                .map(poTolerancePolicyMapper::toDomain);
    }
}