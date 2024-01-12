package org.guzzing.studay_data_invocator.academy.category.maker;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;

public interface CategoryMaker {

    boolean isContains(GyeonggiSourceAcademy gyeonggiSourceAcademy);

    boolean isContains(SeoulSourceAcademy seoulSourceAcademy);

}
