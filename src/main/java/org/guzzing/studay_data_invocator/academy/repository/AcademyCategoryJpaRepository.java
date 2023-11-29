package org.guzzing.studay_data_invocator.academy.repository;

import org.guzzing.studay_data_invocator.academy.model.AcademyCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcademyCategoryJpaRepository extends JpaRepository<AcademyCategory, Long>, AcademyCategoryRepository {

    AcademyCategory save(AcademyCategory academyCategory);

}
