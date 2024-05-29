package org.guzzing.studay_data_invocator.academy.category.maker;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.category.AreaOfExpert;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.CategoryName;

import java.util.List;

public class MathCategoryMaker implements CategoryMaker {

    private static final String MATH = "수학";

    public boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        if (CategoryFilter.isContains(
                List.of(AreaOfExpert.ETC.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of(MATH), gyeonggiSourceAcademy.getAcademyName())) {
            return true;
        }

        if (CategoryFilter.isContains(
                List.of(AreaOfExpert.TUTORING.getValue(), AreaOfExpert.TOTAL.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of(MATH), gyeonggiSourceAcademy.getLessonCurriculum())) {
            return true;
        }

        return false;
    }

    public boolean isContains(SeoulSourceAcademy seoulSourceAcademy) {
        if(CategoryFilter.isContains(
                List.of(CategoryName.MATH.getValue()),
                seoulSourceAcademy.getContentsOfCourseFeePerPerson()
        )){
            return true;
        }
        if(CategoryFilter.isContains(
                List.of(CategoryName.MATH.getValue()),
                seoulSourceAcademy.getAcademyName()
        )){
            return true;
        }

        return false;
    }
}
