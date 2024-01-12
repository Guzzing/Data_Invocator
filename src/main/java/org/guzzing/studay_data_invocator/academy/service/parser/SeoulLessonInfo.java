package org.guzzing.studay_data_invocator.academy.service.parser;

public record SeoulLessonInfo(
        String lessonName,
        String duration ,
        String lessonPrice
) {
    public static SeoulLessonInfo of (
            String lessonName,
            String duration ,
            String lessonPrice
    ) {
        return new SeoulLessonInfo(
                lessonName,
                duration,
                lessonPrice
        );
    }

    public static SeoulLessonInfo of (
            String lessonName,
            String lessonPrice
    ) {
        return new SeoulLessonInfo(
                lessonName,
                "",
                lessonPrice
        );
    }
}
