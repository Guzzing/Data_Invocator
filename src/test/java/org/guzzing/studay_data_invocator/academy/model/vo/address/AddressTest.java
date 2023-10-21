package org.guzzing.studay_data_invocator.academy.model.vo.address;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.junit.jupiter.api.Assertions.*;

import java.text.MessageFormat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    @DisplayName("주소 정보 객체 생성에 성공한다.")
    void instance_Address_Success() {
        // Given
        String dosi = "경기도";
        String sigungu = "성남시";
        String upmyeondong = "판교동";
        String addressDetail = MessageFormat.format("{0} {1} {2} 대왕판교로 13323", dosi, sigungu, upmyeondong);

        // When
        Address result = new Address(addressDetail);

        // Then
        assertThat(result.getDosi()).isEqualTo(dosi);
        assertThat(result.getSigungu()).isEqualTo(sigungu);
        assertThat(result.getUpmyeondong()).isEqualTo(upmyeondong);
    }

    @Test
    @DisplayName("주소 정보 객체 생성에 실패한다.")
    void instance_address_Fail() {
        // Given
        String dosi = "판교동";
        String sigungu = "제주도";
        String upmyeondong = "구로구";
        String addressDetail = dosi + " " + sigungu + " " + upmyeondong + " 대왕판교로 123";

        // When
        Exception exception = catchException(() -> new Address(addressDetail));

        // Then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}