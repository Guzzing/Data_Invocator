package org.guzzing.studay_data_invocator.academy.model.vo.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import java.text.MessageFormat;
import org.guzzing.studay_data_invocator.academy.model.vo.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

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
