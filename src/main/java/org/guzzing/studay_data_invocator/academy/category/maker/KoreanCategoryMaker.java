package org.guzzing.studay_data_invocator.academy.category.maker;

import org.guzzing.studay_data_invocator.academy.category.AreaOfExpert;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.CategoryName;

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

    public boolean isContains(SeoulSourceAcademy seoulSourceAcademy) {
        if(CategoryFilter.isContains(
                List.of("외국어", "중국어", "영국어","한국어"),
                seoulSourceAcademy.getAcademyName()
        )){
            return false;
        }
        if(CategoryFilter.isContains(
                List.of("외국어", "중국어", "영국어","한국어"),
                seoulSourceAcademy.getContentsOfCourseFeePerPerson()
        )){
            return false;
        }
        if(CategoryFilter.isContains(
                List.of(CategoryName.KOREAN_LANGUAGE.getValue()),
                seoulSourceAcademy.getContentsOfCourseFeePerPerson()
        )){
            return true;
        }
        if(CategoryFilter.isContains(
                List.of(CategoryName.KOREAN_LANGUAGE.getValue()),
                seoulSourceAcademy.getAcademyName()
        )){
            return true;
        }

        return false;
    }

}
