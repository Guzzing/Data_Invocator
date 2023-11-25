package org.guzzing.studay_data_invocator;

import org.guzzing.studay_data_invocator.region.RegionDataInvocatorRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudayDataInvocatorApplicationTests {

    @Autowired
    private RegionDataInvocatorRunner runner;

    @Test
    @DisplayName("법정동 데이터 파싱 실행")
    void invocateData_Region() {
        runner.invocateData();
    }

}
