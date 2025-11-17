package io.lchangha.logisticsexam.masterdata.location.infra;

import io.lchangha.logisticsexam.masterdata.location.infra.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLocationRepository extends JpaRepository<LocationEntity, Long> {
}
