package org.guzzing.studay_data_invocator.academy.service.category;

import lombok.Getter;

@Getter
public enum AreaOfExpert {
    ETC("기타(대)"),
    TOTAL("종합(대)"),
    TUTORING("입시, 검정 및 보습"),
    ARTS_AND_PHYSICAL_EDUCATION("예능(대)"),
    ART_AND_ART("기예(대)"),
    INTERNATIONALIZATION("국제화"),
    READING_ROOM("독서실"),
    SPECIAL_EDUCATION("특수교육");

    private final String value;

    AreaOfExpert(String value) {
        this.value = value;
    }

}
