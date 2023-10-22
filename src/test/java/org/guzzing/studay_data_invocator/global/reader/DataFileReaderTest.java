package org.guzzing.studay_data_invocator.global.reader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.guzzing.studay_data_invocator.academy.data_parser.meta.AcademyDataFile.SEONGNAM;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DataFileReaderTest {

    @Autowired
    private DataFileReader dataFileReader;

    @Test
    @DisplayName("원본 CSV 파일을 읽는다.")
    void readLine_Success() throws IOException {
        // Given
        String fileName = SEONGNAM.getFileName();

        // When
        List<String> result = dataFileReader.readFileData(fileName);

        // Then
        assertThat(result).isNotEmpty();
    }

}