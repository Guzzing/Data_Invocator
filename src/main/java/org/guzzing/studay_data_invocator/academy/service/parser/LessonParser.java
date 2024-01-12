package org.guzzing.studay_data_invocator.academy.service.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LessonParser {
    public static List<SeoulLessonInfo> parseLessons(String input) {
        List<SeoulLessonInfo> seoulLessonInfos = new ArrayList<>();

        String pattern = "([가-힣\\d]+)(?:\\(([^:)]+)\\))?(?::(\\d+))?"; // 수정된 패턴
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(input);

        while (matcher.find()) {
            String lessonName = matcher.group(1).trim();  // trim()을 추가하여 앞뒤의 공백을 제거
            String duration = matcher.group(2);
            String lessonPrice = matcher.group(3);

            if(lessonPrice == null || lessonPrice.isBlank()) {
                lessonPrice = "0";
            }

            if(duration != null){
                if(!duration.contains("주") ||!duration.contains("분")||!duration.contains("회")) {
                    lessonName = lessonName + duration;
                    seoulLessonInfos.add(SeoulLessonInfo.of(lessonName, lessonPrice));
                    continue;
                }
            }
            seoulLessonInfos.add(SeoulLessonInfo.of(lessonName,duration,lessonPrice));
        }

        return seoulLessonInfos;
    }
}
