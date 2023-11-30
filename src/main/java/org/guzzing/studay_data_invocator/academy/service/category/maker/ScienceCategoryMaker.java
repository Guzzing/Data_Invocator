package org.guzzing.studay_data_invocator.academy.service.category.maker;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.service.category.AreaOfExpert;

import java.util.List;

public class ScienceCategoryMaker implements CategoryMaker {

    private static final String SCIENCE = "과학";

    public boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        if (CategoryFilter.isContains(
                List.of(AreaOfExpert.ETC.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of(SCIENCE), gyeonggiSourceAcademy.getAcademyName())) {
            return true;
        }
        if (CategoryFilter.isContains(
                List.of(AreaOfExpert.TUTORING.getValue(),
                        AreaOfExpert.TOTAL.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of(SCIENCE), gyeonggiSourceAcademy.getLessonCurriculum())) {
            return true;
        }
        return false;
    }

}
