package org.guzzing.studay_data_invocator.academy.model.vo.academy_info;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;
import static org.guzzing.studay_data_invocator.academy.model.vo.academy_info.ShuttleAvailability.NEED_INQUIRE;

import org.guzzing.studay_data_invocator.academy.model.vo.AcademyInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AcademyInfoTest {

    @Test
    @DisplayName("학원 정보 객체를 성공적으로 생성한다.")
    void instance_AcademyInfo_Success() {
        // Given
        String name = "학원1";
        String contact = "010-1234-4321";
        String shuttle = "0";

        // When
        AcademyInfo academyInfo = AcademyInfo.of(name, contact, shuttle);

        // Then
        assertThat(academyInfo.getName()).isEqualTo(name);
        assertThat(academyInfo.getContact()).isEqualTo(contact);
        assertThat(academyInfo.getShuttle()).isEqualTo(NEED_INQUIRE.name());
    }

    @Test
    @DisplayName("학원 정보 객체 생성에 실패한다.")
    void instance_AcademyInfo_Fail() {
        // Given
        String name = " ";
        String contact = "";
        String shuttle = "-1";

        // When
        Exception exception = catchException(() -> AcademyInfo.of(name, contact, shuttle));

        // Then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
    }

}