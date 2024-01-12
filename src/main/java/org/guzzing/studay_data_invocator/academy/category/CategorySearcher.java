package org.guzzing.studay_data_invocator.academy.category;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.CategoryName;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CategorySearcher {
    private final CategoryStrategy categoryStrategy;

    public CategorySearcher(CategoryStrategy categoryStrategy) {
        this.categoryStrategy = categoryStrategy;
    }

    public ApplicableCategoryInfo getCategoryIds(GyeonggiSourceAcademy gyeonggiSourceAcademy) {
        List<Long> categoryIds = new ArrayList<>();
        Arrays.stream(CategoryName.values()).forEach(
                categoryName -> {
                    if (categoryStrategy.isContains(
                            categoryName.name(), gyeonggiSourceAcademy)) {
                        categoryIds.add(categoryName.getId());
                    }
                }
        );

        return ApplicableCategoryInfo.of(
                Long.valueOf(gyeonggiSourceAcademy.getHashCodeValue()),
                categoryIds
        );
    }

    public ApplicableCategoryInfo getCategoryIds(SeoulSourceAcademy seoulSourceAcademy) {
        List<Long> categoryIds = new ArrayList<>();
        Arrays.stream(CategoryName.values()).forEach(
                categoryName -> {
                    if (categoryStrategy.isContains(
                            categoryName.name(), seoulSourceAcademy)) {
                        categoryIds.add(categoryName.getId());
                    }
                }
        );

        return ApplicableCategoryInfo.of(
                Long.valueOf(seoulSourceAcademy.getAcademyDesignationNumber()),
                categoryIds
        );
    }
}
