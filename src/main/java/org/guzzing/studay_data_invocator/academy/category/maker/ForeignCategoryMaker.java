package org.guzzing.studay_data_invocator.academy.category.maker;

import org.guzzing.studay_data_invocator.academy.category.AreaOfExpert;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.CategoryName;

import java.util.List;

public class ForeignCategoryMaker implements CategoryMaker {

    public boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        if (gyeonggiSourceAcademy.getAreaOfExpert().equals(AreaOfExpert.TOTAL.getValue())
                && gyeonggiSourceAcademy.getLessonLine().equals("국제")) {
            return true;
        }
        if (gyeonggiSourceAcademy.getAreaOfExpert().equals(AreaOfExpert.TOTAL.getValue())
                && gyeonggiSourceAcademy.getLessonLine().equals("외국어")) {
            return true;
        }
        if (gyeonggiSourceAcademy.getAreaOfExpert().equals(AreaOfExpert.INTERNATIONALIZATION.getValue())) {
            return true;
        }

        return false;
    }


    public boolean isContains(SeoulSourceAcademy seoulSourceAcademy) {
        if(CategoryFilter.isContains(
                List.of(CategoryName.FOREIGN_LANGUAGE.getValue()),
                seoulSourceAcademy.getTeachingDivisionName()
        )){
            return true;
        }
        if(CategoryFilter.isContains(
                List.of("국제"),
                seoulSourceAcademy.getTeachingDivisionName()
        )){
            return true;
        }

        return false;
    }

}
