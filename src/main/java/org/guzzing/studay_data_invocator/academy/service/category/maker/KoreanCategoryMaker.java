package org.guzzing.studay_data_invocator.academy.service.category.maker;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.service.category.AreaOfExpert;

import java.util.List;

public class KoreanCategoryMaker implements CategoryMaker {

    private static final String KOREAN_LANGUAGE = "국어";

    public boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        List<String> notLikes = List.of("외국어", "중국어", "영국어");
        List<String> likes = List.of(KOREAN_LANGUAGE);

        if (gyeonggiSourceAcademy.getAreaOfExpert().equals(AreaOfExpert.ETC.getValue())
                && !CategoryFilter.isContains(notLikes, gyeonggiSourceAcademy.getLessonCurriculum())
                && CategoryFilter.isContains(likes, gyeonggiSourceAcademy.getLessonCurriculum())) {
            return true;
        }

        if (gyeonggiSourceAcademy.getAreaOfExpert().equals(AreaOfExpert.TUTORING.getValue())
                && CategoryFilter.isContains(likes, gyeonggiSourceAcademy.getLessonCurriculum())) {
            return true;
        }

        return false;
    }

}
