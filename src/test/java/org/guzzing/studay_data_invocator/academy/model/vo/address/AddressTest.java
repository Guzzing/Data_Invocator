package org.guzzing.studay_data_invocator.academy.model.vo.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import org.guzzing.studay_data_invocator.academy.model.vo.Address;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    @DisplayName("주소 정보 객체 생성에 실패한다.")
    void instance_address_Fail() {
        // Given
        String fullAddress = " ";

        // When
        Exception exception = catchException(() -> Address.of(fullAddress));

        // Then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}
