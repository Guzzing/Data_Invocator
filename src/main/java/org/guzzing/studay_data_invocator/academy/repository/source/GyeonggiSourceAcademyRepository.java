package org.guzzing.studay_data_invocator.academy.repository.source;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;

import java.util.List;

public interface GyeonggiSourceAcademyRepository {

    List<GyeonggiSourceAcademy> findAll();
}
