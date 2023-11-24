package org.guzzing.studay_data_invocator.academy.infra.xlsx;

import org.apache.poi.ss.usermodel.Cell;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;


import java.util.List;

import static org.guzzing.studay_data_invocator.academy.infra.xlsx.AcademyColumnIndex.*;
import static org.guzzing.studay_data_invocator.academy.infra.xlsx.AcademyColumnIndex.TEACHER_COUNT;

public class CellParser {

    public static GyeonggiSourceAcademy parseCell(List<String> cells) {
        String regionName = cells.get(REGION_NAME.ordinal());
        String academyName = cells.get(ACADEMY_NAME.ordinal());
        String academyType = cells.get(ACADEMY_TYPE.ordinal());
        String areaOfExpert = cells.get(AREA_OF_EXPERT.ordinal());
        String address = cells.get(ADDRESS.ordinal());
        String representative = cells.get(REPRESENTATIVE.ordinal());
        String contact = cells.get(CONTACT.ordinal());
        String lessonTime = cells.get(LESSON_LINE.ordinal());
        String lessonSubject = cells.get(LESSON_SUBJECT.ordinal());
        String lessonCurriculum = cells.get(LESSON_CURRICULUM.ordinal());
        long lessonCapacity = getInt(cells.get(LESSON_CAPACITY.ordinal()));
        String lessonDuration = cells.get(LESSON_DURATION.ordinal());
        long teachingTime = getInt(cells.get(TEACHING_TIME.ordinal()));
        long tuitionFee = getInt(cells.get(TUITION_FEE.ordinal()));
        long mockTestFee = getInt(cells.get(MOCK_TEST_FEE.ordinal()));
        long materialFee = getInt(cells.get(MATERIAL_FEE.ordinal()));
        long mealFee = getInt(cells.get(MEAL_FEE.ordinal()));
        long dormitoryFee = getInt(cells.get(DORMITORY_FEE.ordinal()));
        long shuttleFee = getInt(cells.get(SHUTTLE_FEE.ordinal()));
        long clothingFee = getInt(cells.get(CLOTHING_FEE.ordinal()));
        long totalFee = getInt(cells.get(TOTAL_FEE.ordinal()));
        long teacherCount = getInt(cells.get(TEACHER_COUNT.ordinal()));

        GyeonggiSourceAcademy gyeonggiSourceAcademy = GyeonggiSourceAcademy.of(
                regionName,
                academyName,
                academyType,
                areaOfExpert,
                address,
                representative,
                contact,
                lessonTime,
                lessonSubject,
                lessonCurriculum,
                lessonCapacity,
                lessonDuration,
                teachingTime,
                tuitionFee,
                mockTestFee,
                materialFee,
                mealFee,
                dormitoryFee,
                shuttleFee,
                clothingFee,
                totalFee,
                teacherCount
        );
        gyeonggiSourceAcademy.setHashCodeValue();

        return gyeonggiSourceAcademy;
    }

    private static String getString(Cell cell) {
        String value = cell.toString();
        if (value.isBlank()) {
            return "";
        }
        return value;
    }

    private static long getInt(String cell) {


        if (cell.isBlank()) {
            return 0;
        }
        try {
            return Long.parseLong(cell);
        } catch (NumberFormatException e) {
            return 0;
        }

    }

}
