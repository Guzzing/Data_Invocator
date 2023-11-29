package org.guzzing.studay_data_invocator.academy.service.parser;

import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.Lesson;

public record AcademyAndLessonInfo(
   Academy academy,
   Lesson lesson
) {
    public static AcademyAndLessonInfo of(
            Academy academy,
            Lesson lesson
    ){
        return new AcademyAndLessonInfo(
                academy,
                lesson
        );
    }
}
