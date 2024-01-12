package org.guzzing.studay_data_invocator.academy.service.parser;

import org.guzzing.studay_data_invocator.academy.model.Academy;
import org.guzzing.studay_data_invocator.academy.model.Lesson;

import java.util.List;

public record AcademiesAndLessonInfos(
        Academy academy,
        List<Lesson> lessons
) {
    public static AcademiesAndLessonInfos of(
            Academy academy,
            List<Lesson> lessons){
        return new AcademiesAndLessonInfos(
                academy,
                lessons
        );
    }
}
