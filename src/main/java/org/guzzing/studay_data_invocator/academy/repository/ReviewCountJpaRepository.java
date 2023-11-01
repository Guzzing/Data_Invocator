package org.guzzing.studay_data_invocator.academy.repository;


import org.guzzing.studay_data_invocator.academy.model.ReviewCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewCountJpaRepository extends JpaRepository<ReviewCount, Long> {

    @Query("select rc from ReviewCount rc where rc.academy.id =: academyId")
    ReviewCount getByAcademyId(Long academyId);
}
