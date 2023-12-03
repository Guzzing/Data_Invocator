package org.guzzing.studay_data_invocator.academy.service.category.maker;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.service.category.AreaOfExpert;

import java.util.List;

public class TutoringCategoryMaker implements CategoryMaker {

    public boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        if (CategoryFilter.isContains(
                List.of(AreaOfExpert.TOTAL.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of("보통", "진학"), gyeonggiSourceAcademy.getLessonLine())) {
            return true;
        }
        if (CategoryFilter.isContains(
                List.of(AreaOfExpert.TUTORING.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())) {
            return true;
        }

        return false;
    }

}
