package org.guzzing.studay_data_invocator.academy.category.maker;

import org.guzzing.studay_data_invocator.academy.category.AreaOfExpert;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.CategoryName;

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
        if (CategoryFilter.isContains(
                List.of(CategoryName.MATH.getValue(),
                        CategoryName.SCIENCE.getValue(),
                        CategoryName.ENGLISH.getValue(),
                        CategoryName.KOREAN_LANGUAGE.getValue()),
                gyeonggiSourceAcademy.getLessonCurriculum())) {
            return true;
        }
        if (CategoryFilter.isContains(
                List.of(CategoryName.TUTORING_SCHOOL.getValue()),
                gyeonggiSourceAcademy.getAcademyName())) {
            return true;
        }

        return false;
    }

    public boolean isContains(SeoulSourceAcademy seoulSourceAcademy) {
        if (CategoryFilter.isContains(
                List.of(CategoryName.TUTORING_SCHOOL.getValue()),
                seoulSourceAcademy.getInstructionalCourseListName())) {
            return true;
        }
        if (CategoryFilter.isContains(
                List.of(CategoryName.TUTORING_SCHOOL.getValue()),
                seoulSourceAcademy.getNameOfTeachingCourse())) {
            return true;
        }
        if (CategoryFilter.isContains(
                List.of("보통교과"),
                seoulSourceAcademy.getTeachingDivisionName())) {
            return true;
        }

        return false;
    }

}
