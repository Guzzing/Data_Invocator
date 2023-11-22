package org.guzzing.studay_data_invocator.sourceacademy;

public enum AcademyColumnIndex {
    REGION_NAME(0),
    ACADEMY_NAME(1),
    ACADEMY_TYPE(2),
    AREA_OF_EXPERT(3),
    ADDRESS(4),
    REPRESENTATIVE(5),
    CONTACT(6),
    LESSON_LINE(7),
    LESSON_SUBJECT(8),
    LESSON_CURRICULUM(9),
    LESSON_CAPACITY(10),
    LESSON_DURATION(11),
    TEACHING_TIME(12),
    TUITION_FEE(13),
    MOCK_TEST_FEE(14),
    MATERIAL_FEE(15),
    MEAL_FEE(16),
    DORMITORY_FEE(17),
    SHUTTLE_FEE(18),
    CLOTHING_FEE(19),
    TOTAL_FEE(20),
    TEACHER_COUNT(21);

    private final int index;

    AcademyColumnIndex(int index) {
        this.index = index;
    }
    
}
