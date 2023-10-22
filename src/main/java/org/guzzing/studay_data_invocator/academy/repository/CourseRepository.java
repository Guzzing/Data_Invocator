package org.guzzing.studay_data_invocator.academy.repository;

import org.guzzing.studay_data_invocator.academy.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
