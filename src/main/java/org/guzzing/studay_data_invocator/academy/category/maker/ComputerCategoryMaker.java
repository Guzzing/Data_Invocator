package org.guzzing.studay_data_invocator.academy.category.maker;

import org.guzzing.studay_data_invocator.academy.category.AreaOfExpert;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.CategoryName;

import java.util.List;

public class ComputerCategoryMaker implements CategoryMaker {

    public boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        if (CategoryFilter.isContains(List.of(AreaOfExpert.TOTAL.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of("정보", "컴퓨터"), gyeonggiSourceAcademy.getLessonLine())) {
            return true;
        }
        if (CategoryFilter.isContains(List.of(AreaOfExpert.TOTAL.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of("기타(중)"), gyeonggiSourceAcademy.getLessonLine())
                && CategoryFilter.isContains(List.of("컴퓨터", "소프트웨어"), gyeonggiSourceAcademy.getLessonSubject())) {
            return true;
        }
        if (CategoryFilter.isContains(List.of(AreaOfExpert.ETC.getValue()), gyeonggiSourceAcademy.getAreaOfExpert())
                && CategoryFilter.isContains(List.of("코딩", "컴퓨터", "아이티"), gyeonggiSourceAcademy.getAcademyName())) {
            return true;
        }

        return false;
    }

    public boolean isContains(SeoulSourceAcademy seoulSourceAcademy) {
        if(CategoryFilter.isContains(
                List.of("정보"),
                seoulSourceAcademy.getTeachingDivisionName()
        )){
            return true;
        }
        if(CategoryFilter.isContains(
                List.of(CategoryName.COMPUTER.getValue()),
                seoulSourceAcademy.getAcademyName()
        )){
            return true;
        }
        if(CategoryFilter.isContains(
                List.of("코딩"),
                seoulSourceAcademy.getAcademyName()
        )){
            return true;
        }
        return false;
    }

}
