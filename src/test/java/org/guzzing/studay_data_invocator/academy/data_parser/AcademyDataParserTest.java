package org.guzzing.studay_data_invocator.academy.data_parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataFile.SEONGNAM;

import java.util.List;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AcademyDataParserTest {

    @Autowired
    private AcademyDataParser dataParser;

    @Test
    @DisplayName("원본 데이터 라인에서 필요한 컬럼만 추출하여 파싱한다.")
    void parseData_Success() {
        // Given
        String fileName = SEONGNAM.getFileName();

        // When
        List<Academy> result = dataParser.parseData(fileName);

        // Then
        String fullAddress = result.get(0).getFullAddress();

        assertThat(result).isNotEmpty();
        assertThat(fullAddress).contains(SEONGNAM.getRegion());
    }

}