package org.guzzing.studay_data_invocator;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.guzzing.studay_data_invocator.global.reader.DataFileReader;
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
        String fileName = "docs/성남-표 1.csv";

        // When
        List<String> result = dataFileReader.readFileData(fileName);

        // Then
        assertThat(result).isNotEmpty();
    }

}