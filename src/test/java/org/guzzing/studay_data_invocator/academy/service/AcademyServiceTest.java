package org.guzzing.studay_data_invocator.academy.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.MessageFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
class AcademyServiceTest {

    @Autowired
    private AcademyService academyService;

    @Test
    @DisplayName("데이터를 성공적으로 불러온다.")
    void importData_Success() {
        // Given
        String expectCityName = "성남";
        String fileName = MessageFormat.format("docs/{0}-표 1.csv", expectCityName);

        // When
        long savedCount = academyService.importData(fileName);

        // Then
        assertThat(savedCount).isPositive();
    }

}