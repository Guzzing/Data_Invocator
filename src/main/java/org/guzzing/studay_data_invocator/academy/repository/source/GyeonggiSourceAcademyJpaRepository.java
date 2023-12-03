package org.guzzing.studay_data_invocator.academy.repository.source;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GyeonggiSourceAcademyJpaRepository extends JpaRepository<GyeonggiSourceAcademy, Long> , GyeonggiSourceAcademyRepository{

    List<GyeonggiSourceAcademy> findAll();
}
