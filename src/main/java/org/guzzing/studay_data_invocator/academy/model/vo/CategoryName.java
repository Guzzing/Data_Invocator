package org.guzzing.studay_data_invocator.academy.model.vo;

import lombok.Getter;

@Getter
public enum CategoryName {
    MATH(1L, "수학"),
    SCIENCE(2L, "과학"),
    KOREAN_LANGUAGE(3L, "국어"),
    ENGLISH(4L, "영어"),
    COMPUTER(5L, "컴퓨터"),
    ARTS_AND_PHYSICAL_EDUCATION(6L, "예체능"),
    FOREIGN_LANGUAGE(7L, "외국어"),
    TUTORING_SCHOOL(8L, "보습"),
    ETC(9L, "기타");

    private final Long id;

    private final String value;

    CategoryName(Long id, String value) {
        this.id = id;
        this.value = value;
    }
}
