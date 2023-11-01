package org.guzzing.studay_data_invocator;

import org.guzzing.studay_data_invocator.academy.service.AcademyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudayDataInvocatorApplicationTests {

    @Autowired
    private AcademyService academyService;

    @Test
    @DisplayName("실행기")
    void contextLoads() {
        academyService.importAllData();
    }

    @Test
    @DisplayName("리뷰 Count 실행기")
    void contextReviewCount() {
        academyService.makeReviewCount();
    }

}
