package org.guzzing.studay_data_invocator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import org.guzzing.studay_data_invocator.academy.data_parser.AcademyDataParser;
import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.global.reader.DataFileReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DataParserTest {

    @Autowired
    private AcademyDataParser dataParser;

    @Autowired
    private DataFileReader dataFileReader;

    @Test
    @DisplayName("원본 데이터 라인에서 필요한 컬럼만 추출하여 파싱한다.")
    void parseData_Success() throws IOException {
        // Given
        String expectCityName = "성남";
        String fileName = MessageFormat.format("docs/{0}-표 1.csv", expectCityName);

        // When
        List<Academy> result = dataParser.parseData(fileName);

        // Then
        String fullAddress = result.get(0).getFullAddress();

        assertThat(result).isNotEmpty();
        assertThat(fullAddress).contains(expectCityName);
    }

}