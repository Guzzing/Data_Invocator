package org.guzzing.studay_data_invocator.region.model.vo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    @DisplayName("시/구/군 하나로만 구분되는 주소인 경우 정상적으로 주소 객체 생성에 성공한다.")
    void instance_OnlyOneField_Success() {
        // Given
        String sido = "경기도";
        String sigungu = "화성군";
        String upmyeondong = "동탄면";

        // When
        Address result = Address.of(sido, sigungu, upmyeondong);

        // Then
        assertThat(result).hasFieldOrPropertyWithValue("sido", sido);
    }

    @Test
    @DisplayName("시군구가 한 번에 주어지는 경우 띄어쓰기로 구분한 주소 객체 생성에 성공한다.")
    void instance_MoreThanTwoField_Success() {
        // Given
        String sido = "경기도";
        String sigungu = "수원시팔달구";
        String upmyeongdong = "호수동";

        // When
        Address result = Address.of(sido, sigungu, upmyeongdong);

        // Then
        String regulatedSigungu = "수원시 팔달구";

        assertThat(result).hasFieldOrPropertyWithValue("sigungu", regulatedSigungu);
    }

    @Test
    @DisplayName("주소 정보가 같으면 같은 주소로 판단한다.")
    void instance_ContainsOtherData_SameAddress() {
        // Given
        Address address1 = Address.of("경기도", "여주시", "금사면");
        Address address2 = Address.of("경기도", "여주시", "금사면");

        // When
        boolean result = Objects.equals(address1, address2);

        // Then
        assertThat(result).isTrue();
    }

}