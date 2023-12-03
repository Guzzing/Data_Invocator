package org.guzzing.studay_data_invocator.academy.repository;

import org.guzzing.studay_data_invocator.academy.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

}
