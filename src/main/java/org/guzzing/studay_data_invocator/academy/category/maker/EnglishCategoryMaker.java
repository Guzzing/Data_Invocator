package org.guzzing.studay_data_invocator.academy.service.category.maker;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.service.category.AreaOfExpert;

import java.util.List;

public class EnglishCategoryMaker implements CategoryMaker {

    private static final String ENGLISH = "영어";

    public boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        if (CategoryFilter.isContains(List.of(AreaOfExpert.TUTORING.getValue(),
                AreaOfExpert.TOTAL.getValue(),
                AreaOfExpert.ETC.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of(ENGLISH), gyeonggiSourceAcademy.getLessonCurriculum())) {
            return true;
        }

        return false;
    }

}
