package org.guzzing.studay_data_invocator.academy.category.maker;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.category.AreaOfExpert;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;

import java.util.List;

public class ArtAndEductionCategoryMaker implements CategoryMaker {

    public boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        if (CategoryFilter.isContains(
                List.of(AreaOfExpert.ART_AND_ART.getValue(), AreaOfExpert.ARTS_AND_PHYSICAL_EDUCATION.getValue()),
                gyeonggiSourceAcademy.getAreaOfExpert())) {
            return true;
        }
        if (CategoryFilter.isContains(List.of(AreaOfExpert.TOTAL.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of("예능", "기예"), gyeonggiSourceAcademy.getLessonLine())) {
            return true;
        }
        if (CategoryFilter.isContains(List.of(AreaOfExpert.ETC.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of("만화", "바둑", "음악", "무용", "댄스", "캐릭터"), gyeonggiSourceAcademy.getLessonSubject())) {
            return true;
        }

        return false;
    }

    public boolean isContains(SeoulSourceAcademy seoulSourceAcademy) {
        if(CategoryFilter.isContains(
                List.of("예능"),
                seoulSourceAcademy.getTeachingDivisionName()
        )){
            return true;
        }
        if(CategoryFilter.isContains(
                List.of("기예"),
                seoulSourceAcademy.getTeachingDivisionName()
        )){
            return true;
        }

        return false;
    }

}
