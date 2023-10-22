package org.guzzing.studay_data_invocator.academy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataFile.SEONGNAM;

import org.guzzing.studay_data_invocator.academy.repository.AcademyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@Transactional
class AcademyServiceTest {

    @Autowired
    private AcademyService academyService;

    @Autowired
    private AcademyRepository academyRepository;

    @Test
    @DisplayName("모든 데이터를 성공적으로 불러온다.")
    void importAllData_Success() {
        // Given & When
        academyService.importAllData();

        // Then
        long count = academyRepository.count();

        assertThat(count).isGreaterThan(1);
    }

    @Test
    @DisplayName("데이터를 성공적으로 불러온다.")
    void importData_Success() {
        // Given
        String fileName = SEONGNAM.getFileName();

        // When
        long savedCount = academyService.importData(fileName);

        // Then
        assertThat(savedCount).isPositive();
    }

}