package org.guzzing.studay_data_invocator.academy.service.category.maker;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.service.category.AreaOfExpert;
import org.guzzing.studay_data_invocator.academy.service.category.maker.CategoryMaker;

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

}
