package org.guzzing.studay_data_invocator.academy.model.vo.address;

import java.util.List;

public enum RegionUnit {
    SIDO(List.of("시", "도")),
    FIRST_SIGUNGU(List.of("시", "군", "구")),
    SECOND_SIGUNGU(List.of( "구")),
    UPMYEONDONG(List.of("읍", "면", "동"));

    private final List<String> unitValue;

    RegionUnit(List<String> unitValue) {
        this.unitValue = unitValue;
    }

    public boolean isMatched(final String input) {
        return this.unitValue.stream()
                .anyMatch(input::contains);
    }

    public static boolean isTwoTokenSigungu(String firstToken, String secondToken) {
        return FIRST_SIGUNGU.isMatched(firstToken) && SECOND_SIGUNGU.isMatched(secondToken);
    }

}
