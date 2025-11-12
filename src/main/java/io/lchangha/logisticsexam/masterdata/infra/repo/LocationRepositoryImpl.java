package io.lchangha.logisticsexam.masterdata.infra.repo;

import io.lchangha.logisticsexam.masterdata.domain.Location;
import io.lchangha.logisticsexam.masterdata.domain.contract.LocationRepository;
import io.lchangha.logisticsexam.masterdata.infra.entity.LocationEntity;
import io.lchangha.logisticsexam.masterdata.infra.mapper.LocationEntityMapper;
import io.lchangha.logisticsexam.shared.domain.page.PageResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Primary
@RequiredArgsConstructor
public class LocationRepositoryImpl implements LocationRepository {

    private final JpaLocationRepository jpaRepository;
    private final LocationEntityMapper mapper;

    @Override
    public Location save(Location location) {
        LocationEntity entity = mapper.toEntity(location);
        LocationEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Location> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public PageResult<Location> findPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<LocationEntity> pages = jpaRepository.findAll(pageable);
        List<Location> list = pages.getContent().stream().map(mapper::toDomain).toList();
        return new PageResult<>(list, page, size, pages.getTotalElements(), pages.getTotalPages(), pages.hasNext());
    }
}
