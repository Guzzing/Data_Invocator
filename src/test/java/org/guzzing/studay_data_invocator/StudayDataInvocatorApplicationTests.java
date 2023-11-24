package org.guzzing.studay_data_invocator;

import org.guzzing.studay_data_invocator.academy.service.AcademyService;
import org.guzzing.studay_data_invocator.sourceacademy.service.GyeonggiSourceAcademyService;
import org.guzzing.studay_data_invocator.region.service.RegionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("commercial")
class StudayDataInvocatorApplicationTests {

    private static final String fileLocation = "docs/data/경기도.xlsx";

    @Autowired
    private AcademyService academyService;

    @Autowired
    private GyeonggiSourceAcademyService gyeonggiSourceAcademyService;

    @Autowired
    private RegionService regionService;

    @Test
    @DisplayName("학원 데이터 파싱 실행기")
    void adcademyDataParsing() {
        academyService.importAllData();
    }

    @Test
    @DisplayName("경기도 원본 학원 데이터 파싱 실행기")
    void loadSourceAcademies() throws Exception {
        gyeonggiSourceAcademyService.saveSourceAcademiesPerfect(fileLocation);
    }

    @Test
    @DisplayName("법정동 데이터 파싱 실행기")
    void regionDataParsing() {
        regionService.importAllData();
    }

}
