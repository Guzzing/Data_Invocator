package org.guzzing.studay_data_invocator.region.service;

import jakarta.persistence.Transient;
import org.guzzing.studay_data_invocator.region.model.Region;
import org.guzzing.studay_data_invocator.region.repository.RegionJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RegionService {

    private final RegionJpaRepository repository;

    public RegionService(RegionJpaRepository repository) {
        this.repository = repository;
    }

    public void saveRegion(Region region) {
        repository.save(region);
    }
}
