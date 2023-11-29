package org.guzzing.studay_data_invocator.academy.service.category;

import java.util.List;

public record ApplicableCategoryInfo(
    Long sourceAcademyIdentifier,
    List<Long> categoryIds)
{

    public static ApplicableCategoryInfo of(
            Long sourceAcademyIdentifier,
            List<Long> categoryIds
    ){
        return new ApplicableCategoryInfo(
                sourceAcademyIdentifier,
                categoryIds
        );
    }
}
