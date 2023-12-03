package org.guzzing.studay_data_invocator;

import org.guzzing.studay_data_invocator.academy.service.AcademyService;
import org.guzzing.studay_data_invocator.academy.service.source.SourceAcademyService;
import org.guzzing.studay_data_invocator.global.config.GeoJsonConfig;
import org.guzzing.studay_data_invocator.region.RegionDataInvocatorRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudayDataInvocatorApplicationTests {

    private static final String fileLocation = "docs/data/경기도.xlsx";

    @Autowired
    private GeoJsonConfig geoJsonConfig;

    @Autowired
    private RegionDataInvocatorRunner runner;

    @Autowired
    private SourceAcademyService sourceAcademyService;

    @Autowired
    private AcademyService academyService;

    @Test
    @DisplayName("법정동 데이터 파싱 실행")
    void invocateData_Region() {
        runner.invocateData(geoJsonConfig.getGyeongGi());
        runner.invocateData(geoJsonConfig.getSeoul());
    }

    @Test
    @DisplayName("경기도 원본 학원 데이터 파싱 실행기")
    void loadSourceAcademies() throws Exception {
        sourceAcademyService.saveGyeonggiSourceAcademies(fileLocation);
    }

    @Test
    void saveSeoulSourceAcademy() {
        sourceAcademyService.saveSeoulSourceAcademyAsync();
    }

    @Test
    @DisplayName("경기도 2차 가공 데이터 파싱 실행기")
    void saveEfficientGyeonggiAcademies() {
        academyService.saveEfficientGyeonggiAcademies();
    }

    @Test
    @DisplayName("아무런 카테고리에 속하지 않는 데이터들은 ETC에 속한다.")
    void makeCategoryForNotHaveCategory() {
        academyService.makeEtcCategory();
    }

}
