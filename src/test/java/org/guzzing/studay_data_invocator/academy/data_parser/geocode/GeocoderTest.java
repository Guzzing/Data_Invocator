package org.guzzing.studay_data_invocator.academy.data_parser.geocode;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.guzzing.studay_data_invocator.academy.data_parser.gecode.Geocoder;
import org.guzzing.studay_data_invocator.global.location.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GeocoderTest {

    @Autowired
    private Geocoder geocoder;

    @Test
    @DisplayName("네이버 지오코드를 이용해 주소를 위경도로 바꿔준다.")
    void addressToLocation_Success() {
        // Given
        String address = "경기도 수원시 영통구 망포로 142 ";

        // When
        Optional<Location> location = geocoder.addressToLocation(address);

        // Then
        assertThat(location.get().getLatitude())
                .isBetween(30.0, 40.0);

        assertThat(location.get().getLongitude())
                .isBetween(124.0, 128.0);
    }

    @Test
    @DisplayName("잘못된 주소로 네이버 지오코드 요청 시 Optional.empty()을 반환한다.")
    void addressToLocation_Fail() {
        // Given
        String address = "잘못된 주소";
        // When & Then
        assertThat(geocoder.addressToLocation(address))
                .isEqualTo(Optional.empty());
    }

}
