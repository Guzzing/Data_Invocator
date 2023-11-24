package org.guzzing.studay_data_invocator.academy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.guzzing.studay_data_invocator.academyparser.AcademyDataFile.SEONGNAM;

import org.guzzing.studay_data_invocator.academy.repository.AcademyRepository;
import org.guzzing.studay_data_invocator.academy.repository.LessonRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AcademyServiceTest {

    @Autowired
    private AcademyService academyService;

    @Autowired
    private AcademyRepository academyRepository;
    @Autowired
    private LessonRepository lessonRepository;

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
        academyService.importData(fileName);

        // Then
        long academyCount = academyRepository.count();
        long courseCount = lessonRepository.count();

        assertThat(academyCount).isGreaterThan(1);
        assertThat(courseCount).isGreaterThan(1);
    }

}
