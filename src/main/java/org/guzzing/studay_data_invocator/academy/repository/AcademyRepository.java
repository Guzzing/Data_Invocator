package org.guzzing.studay_data_invocator.academy.repository;

import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.AcademyCategory;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AcademyRepository extends JpaRepository<Academy, Long> {

    @Query(
            value = "SELECT  a FROM Academy as a " +
                    "LEFT JOIN AcademyCategory as ac ON a.id = ac.academy.id  " +
                    "WHERE ac.academy.id IS NULL"
    )
    List<Academy> findAllNotCategory();

}
