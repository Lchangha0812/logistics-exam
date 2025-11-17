package io.lchangha.logisticsexam.receiving.infra.repo;

import io.lchangha.logisticsexam.receiving.domain.GoodsReceipt;
import io.lchangha.logisticsexam.receiving.domain.PoTolerancePolicy;
import io.lchangha.logisticsexam.receiving.domain.contract.GoodsReceiptRepository;
import io.lchangha.logisticsexam.receiving.domain.contract.PoTolerancePolicyRepository;
import io.lchangha.logisticsexam.receiving.infra.mapper.GoodsReceiptMapper;
import io.lchangha.logisticsexam.receiving.infra.repo.entity.GoodsReceiptEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class GoodsReceiptRepositoryImpl implements GoodsReceiptRepository {

    private final JpaGoodsReceiptRepository jpaGoodsReceiptRepository;
    private final PoTolerancePolicyRepository poTolerancePolicyRepository;
    private final GoodsReceiptMapper goodsReceiptMapper;
    private final EntityManager entityManager;

    public GoodsReceiptRepositoryImpl(JpaGoodsReceiptRepository jpaGoodsReceiptRepository,
                                      PoTolerancePolicyRepository poTolerancePolicyRepository,
                                      GoodsReceiptMapper goodsReceiptMapper,
                                      EntityManager entityManager) {
        this.jpaGoodsReceiptRepository = jpaGoodsReceiptRepository;
        this.poTolerancePolicyRepository = poTolerancePolicyRepository;
        this.goodsReceiptMapper = goodsReceiptMapper;
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public GoodsReceipt save(GoodsReceipt goodsReceipt) {
        GoodsReceiptEntity entity = goodsReceiptMapper.toEntity(goodsReceipt);
        GoodsReceiptEntity savedEntity = jpaGoodsReceiptRepository.save(entity);
        entityManager.detach(savedEntity);

        PoTolerancePolicy poTolerancePolicy = null;
        if (savedEntity.getPoTolerancePolicyId() != null) {
            poTolerancePolicy = poTolerancePolicyRepository.findById(savedEntity.getPoTolerancePolicyId()).orElse(null);
        }
        return goodsReceiptMapper.toDomain(savedEntity, poTolerancePolicy);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GoodsReceipt> findById(Long id) {
        return jpaGoodsReceiptRepository.findById(id)
                .map(entity -> {
                    PoTolerancePolicy poTolerancePolicy = null;
                    if (entity.getPoTolerancePolicyId() != null) {
                        poTolerancePolicy = poTolerancePolicyRepository.findById(entity.getPoTolerancePolicyId()).orElse(null);
                    }
                    return goodsReceiptMapper.toDomain(entity, poTolerancePolicy);
                });
    }
}