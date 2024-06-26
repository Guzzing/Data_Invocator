package org.guzzing.studay_data_invocator.academy.repository.source;

import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeoulSourceAcademyRepository {

    List<SeoulSourceAcademy> findAllByNotNotNull();

    SeoulSourceAcademy save(SeoulSourceAcademy seoulSourceAcademy);

}
