package org.guzzing.studay_data_invocator.region.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.guzzing.studay_data_invocator.region.model.Region;
import org.guzzing.studay_data_invocator.region.repository.RegionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.config.location=classpath:/application-test.yaml")
//@Transactional
class RegionServiceTest {

    @Autowired
    private RegionService regionService;

    @Autowired
    private RegionRepository regionRepository;

    @Test
    @DisplayName("서울, 경기의 모든 법정동 데이터를 저장한다.")
    void importAllData_SaveSeoulAndGyeonggiData() {
        // Given & When
        regionService.importAllData();

        // Then
        List<Region> result = regionRepository.findAll();

        assertThat(result).isNotEmpty();
        assertThat(result.get(0).getSido()).containsAnyOf("서울시", "경기도");
    }

}