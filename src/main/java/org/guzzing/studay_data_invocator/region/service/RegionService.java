package org.guzzing.studay_data_invocator.region.service;

import org.guzzing.studay_data_invocator.region.repository.RegionJpaRepository;
import org.springframework.stereotype.Service;

@Service
public class RegionService {

    private final RegionJpaRepository repository;

    public RegionService(RegionJpaRepository repository) {
        this.repository = repository;
    }
}
