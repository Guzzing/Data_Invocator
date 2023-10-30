package org.guzzing.studay_data_invocator.academy.model.vo.address;

import io.micrometer.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AddressParser {

    private static final String DELIMITER = " ";
    private static final int MINIMUM_TOKENS = 3;

    public static List<String> parseAddress(String fullAddress) {
        validateInput(fullAddress);
        return parse(fullAddress);
    }

    private static List<String> parse(String fullAddress) {
        List<String> parsingResult = new ArrayList<>();

        String[] splitAddress = fullAddress.split(DELIMITER);
        parsingResult.add(AddressValidator.validateSido(splitAddress[0]));

        if (RegionUnit.isTwoTokenSigungu(splitAddress[1], splitAddress[2])) {
            parsingResult.add(splitAddress[1] + DELIMITER + splitAddress[2]);
            parsingResult.add(splitAddress[3]);

            return parsingResult;
        }

        parsingResult.add(AddressValidator.validateSigungu(splitAddress[1]));
        parsingResult.add(AddressValidator.validateBeopjungdong(splitAddress[2]));

        return parsingResult;
    }

    private static void validateInput(String address) {
        if (StringUtils.isBlank(address) || address.split(DELIMITER).length < MINIMUM_TOKENS) {
            throw new IllegalArgumentException("주소 정보는 최소한 시도, 시군구, 읍면동 세 가지 요소를 가져야 합니다.");
        }
    }


}
