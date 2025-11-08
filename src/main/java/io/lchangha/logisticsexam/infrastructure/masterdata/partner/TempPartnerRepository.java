package io.lchangha.logisticsexam.infrastructure.masterdata.partner;

import io.lchangha.logisticsexam.domain.masterdata.partner.Partner;
import io.lchangha.logisticsexam.domain.masterdata.partner.contract.PartnerRepository;
import io.lchangha.logisticsexam.domain.masterdata.partner.vo.PartnerId;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class TempPartnerRepository implements PartnerRepository {
    private final Map<PartnerId, Partner> partners = new HashMap<>();

    // The interface does not define a save method, so it's not implemented here.
    // To make this temp repository useful, data could be pre-loaded,
    // e.g., in a @PostConstruct block.

    @Override
    public Optional<Partner> findById(PartnerId partnerId) {
        return Optional.ofNullable(partners.get(partnerId));
    }
}
