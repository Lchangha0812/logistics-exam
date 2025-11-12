package io.lchangha.logisticsexam.masterdata.infra.repo;

import io.lchangha.logisticsexam.masterdata.infra.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLocationRepository extends JpaRepository<LocationEntity, Long> {
}
