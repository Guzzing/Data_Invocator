package org.guzzing.studay_data_invocator.region.data_parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.guzzing.studay_data_invocator.region.data_parser.dto.BeopjungdongResponses;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BeopjungdongDataParserTest {

    @Autowired
    private BeopjungdongDataParser beopjungdongDataParser;

    @Test
    @DisplayName("법정동 데이터 요청에 성공한다.")
    void parseData_Success() {
        // Given
        final int pageNumber = 1;

        // When
        BeopjungdongResponses result = beopjungdongDataParser.parseData(pageNumber);

        // Then
        assertThat(result.data()).isNotEmpty();
    }

}