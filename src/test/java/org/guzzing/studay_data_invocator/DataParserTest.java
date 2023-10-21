package org.guzzing.studay_data_invocator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DataParserTest {

    @Autowired
    private DataParser dataParser;

    @Autowired
    private DataFileReader dataFileReader;

    @Test
    @DisplayName("원본 데이터 라인에서 필요한 컬럼만 추출하여 파싱한다.")
    void parseData_Success() throws IOException {
        // Given
        String expectCityName = "성남";
        String fileName = MessageFormat.format("docs/{0}-표 1.csv", expectCityName);

        // When
        List<List<String>> result = dataParser.parseData(fileName);

        // Then
        String address = result.get(0).get(3);

        assertThat(result).isNotEmpty();
        assertThat(address).contains(expectCityName);
    }

}