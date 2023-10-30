package org.guzzing.studay_data_invocator.academy.model.vo.address;

import io.micrometer.common.util.StringUtils;

import static org.guzzing.studay_data_invocator.academy.model.vo.address.RegionUnit.*;

public class AddressValidator {

    public static String validateSido(String sido) {
        return validateAndReturn(sido, SIDO);
    }

    public static String validateSigungu(String sigungu) {
        return validateAndReturn(sigungu, FIRST_SIGUNGU);
    }

    public static String validateBeopjungdong(String beopjungdong) {
        return validateAndReturn(beopjungdong, UPMYEONDONG);
    }

    private static String validateAndReturn(String input, RegionUnit regionUnit) {
        if (StringUtils.isBlank(input) || !regionUnit.isMatched(input)) {
            throw new IllegalArgumentException("올바르지 않은 " + regionUnit + " 구분입니다.");
        }
        return input;
    }
}
