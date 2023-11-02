package org.guzzing.studay_data_invocator;

import org.guzzing.studay_data_invocator.academy.service.AcademyService;
import org.guzzing.studay_data_invocator.region.service.RegionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("commercial")
class StudayDataInvocatorApplicationTests {

    @Autowired
    private AcademyService academyService;

    @Autowired
    private RegionService regionService;

    @Test
    @DisplayName("학원 데이터 파싱 실행기")
    void adcademyDataParsing() {
        academyService.importAllData();
    }

    @Test
    @DisplayName("법정동 데이터 파싱 실행기")
    void regionDataParsing() {
        regionService.importAllData();
    }

    @Test
    @DisplayName("리뷰 Count 실행기")
    void contextReviewCount() {
        academyService.makeReviewCount();
    }

}
