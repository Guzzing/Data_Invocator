package org.guzzing.studay_data_invocator.academy.data_parser.geocode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.guzzing.studay_data_invocator.academy.data_parser.gecode.Geocoder;
import org.guzzing.studay_data_invocator.academy.model.vo.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeocoderTest {

    @Autowired
    private Geocoder geocoder;

    @Test
    @DisplayName("RestTemplate 방식으로 네이버 지오코드를 이용해 주소를 위경도로 바꿔준다.")
    void addressToLocationV1_Success() {
        // Given
        String address = "경기도 성남시 중원구 시민로6번길 4 / 3층 일부 (하대원동)";

        // When
        Location location = geocoder.addressToLocationV1(address);

        // Then
        assertThat(location.getLatitude())
                .isBetween(30.0, 40.0);

        assertThat(location.getLongitude())
                .isBetween(124.0, 128.0);
    }

    @Test
    @DisplayName("WebClient 방식으로 네이버 지오코드를 이용해 주소를 위경도로 바꿔준다.")
    void addressToLocationV2_Success() {
        // Given
        String address = "경기도 성남시 중원구 시민로6번길 4 / 3층 일부 (하대원동)";

        // When
        Location location = geocoder.addressToLocationV2(address);

        // Then
        assertThat(location.getLatitude())
                .isBetween(30.0, 40.0);

        assertThat(location.getLongitude())
                .isBetween(124.0, 128.0);
    }

    @Test
    @DisplayName("WebClient 방식으로 네이버 지오코드를 이용해 주소를 위경도로 바꿔줬는데 못 찾으면 실패한다.")
    void addressToLocationV2_Fail() {
        // Given
        String address = "경기도 성남시 분당구 운중로277번길 46-8 / 3층 일부 (판교동)";

        // When & Then
        assertThatThrownBy(() -> geocoder.addressToLocationV2(address))
                .isInstanceOf();
    }

}