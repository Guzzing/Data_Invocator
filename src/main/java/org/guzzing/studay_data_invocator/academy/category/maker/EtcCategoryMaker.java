package org.guzzing.studay_data_invocator.academy.category.maker;

import org.guzzing.studay_data_invocator.academy.category.AreaOfExpert;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;

import java.util.List;

public class EtcCategoryMaker implements CategoryMaker {

    public boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        if (CategoryFilter.isContains(
                List.of(
                        AreaOfExpert.READING_ROOM.getValue(),
                        AreaOfExpert.SPECIAL_EDUCATION.getValue()),
                gyeonggiSourceAcademy.getAreaOfExpert())) {
            return true;
        }
        if (CategoryFilter.isContains(
                List.of(AreaOfExpert.TUTORING.getValue()),
                gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of("기타(중)"), gyeonggiSourceAcademy.getLessonLine())
                && CategoryFilter.isContains(List.of("전산회계"), gyeonggiSourceAcademy.getLessonSubject())) {
            return true;
        }

        return false;
    }

    public boolean isContains(SeoulSourceAcademy seoulSourceAcademy) {
        if (CategoryFilter.isContains(
                List.of("산업", "경영", "인문사회", "간호보조기술", "문화관광"),
                seoulSourceAcademy.getTeachingDivisionName()
        )) {
            return false;
        }

        return false;
    }

}
