package org.guzzing.studay_data_invocator.region.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.guzzing.studay_data_invocator.region.model.Address;
import org.guzzing.studay_data_invocator.region.model.Area;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddressDataParserTest {

    @Autowired
    private AddressDataParser addressDataParser;

    @Test
    @DisplayName("법정동 데이터 요청")
    void parseData() {
        // Given
        String upmyeondong = "신봉동";
        Area area = new Area(41465105, upmyeondong, null);

        // When
        Address address = addressDataParser.parseData(area);

        // Then
        assertThat(address.getUpmyeondong()).isEqualTo(upmyeondong);
    }

}