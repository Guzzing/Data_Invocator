package org.guzzing.studay_data_invocator;

import org.guzzing.studay_data_invocator.academy.service.AcademyService;
import org.guzzing.studay_data_invocator.sourceacademy.service.SourceAcademyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudayDataInvocatorApplicationTests {

    private static final String fileLocation = "docs/data/경기도.xlsx";

    @Autowired
    private AcademyService academyService;

    @Autowired
    private SourceAcademyService sourceAcademyService;

    @Test
    void contextLoads() {
        academyService.importAllData();
    }

    @Test
    void loadSourceAcademies() throws Exception {
        sourceAcademyService.saveSourceAcademiesPerfect(fileLocation);
    }

}
