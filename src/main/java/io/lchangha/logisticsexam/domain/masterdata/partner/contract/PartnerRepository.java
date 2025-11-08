package io.lchangha.logisticsexam.domain.masterdata.partner.contract;

import io.lchangha.logisticsexam.domain.masterdata.partner.Partner;
import io.lchangha.logisticsexam.domain.masterdata.partner.vo.PartnerId;

import java.util.Optional;

public interface PartnerRepository {
    Optional<Partner> findById(PartnerId partnerId);
}
