package org.guzzing.studay_data_invocator.academy.service.category;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.CategoryName;
import org.guzzing.studay_data_invocator.academy.service.category.maker.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CategoryStrategy {

    private static Map<String, CategoryMaker> categoryMakers = new HashMap<>();

    public CategoryStrategy() {
        categoryMakers.put(CategoryName.ETC.name(), new EtcCategoryMaker());
        categoryMakers.put(CategoryName.ARTS_AND_PHYSICAL_EDUCATION.name(), new ArtAndEductionCategoryMaker());
        categoryMakers.put(CategoryName.COMPUTER.name(), new ComputerCategoryMaker());
        categoryMakers.put(CategoryName.ENGLISH.name(), new EnglishCategoryMaker());
        categoryMakers.put(CategoryName.FOREIGN_LANGUAGE.name(), new ForeignCategoryMaker());
        categoryMakers.put(CategoryName.KOREAN_LANGUAGE.name(), new KoreanCategoryMaker());
        categoryMakers.put(CategoryName.MATH.name(), new MathCategoryMaker());
        categoryMakers.put(CategoryName.SCIENCE.name(), new ScienceCategoryMaker());
        categoryMakers.put(CategoryName.TUTORING_SCHOOL.name(), new TutoringCategoryMaker());
    }

    public boolean isContains(String categoryName, GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        return categoryMakers.get(categoryName).isContains(gyeonggiSourceAcademy);
    }
}
