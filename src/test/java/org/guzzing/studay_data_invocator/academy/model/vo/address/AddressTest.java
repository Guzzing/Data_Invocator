package org.guzzing.studay_data_invocator.academy.model.vo.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.text.MessageFormat;
import org.guzzing.studay_data_invocator.academy.model.vo.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    @DisplayName("주소 정보 객체 생성에 성공한다.")
    void instance_Address_Success() {
        // Given
        String dosi = "경기도";
        String sigungu = "성남시";
        String beopjungdong = "판교동";
        String fullAddress = MessageFormat.format("{0} {1} {2} 대왕판교로 13323", dosi, sigungu, beopjungdong);

        // When
        Address result = Address.of(fullAddress);

        // Then
        assertThat(result.getSido()).isEqualTo(dosi);
        assertThat(result.getSigungu()).isEqualTo(sigungu);
        assertThat(result.getBeopjungdong()).isEqualTo(beopjungdong);
    }

    @Test
    @DisplayName("주소 정보 객체 생성에 실패한다.")
    void instance_address_Fail() {
        // Given
        String sido = "판교동";
        String sigungu = "제주도";
        String beopjungdong = "구로구";
        String addressDetail = MessageFormat.format("{0} {1} {2} 대왕판교로 123", sido, sigungu, beopjungdong);

        // When
        Exception exception = catchException(() -> Address.of(addressDetail));

        // Then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}