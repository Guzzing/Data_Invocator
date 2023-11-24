package org.guzzing.studay_data_invocator.academyparser;

public enum AcademyDataColumnIndex {
    ACADEMY_REGION(0),
    ACADEMY_NAME(1),
    ACADEMY_TYPE(2),
    COURSE_TYPE(3),
    ACADEMY_ADDRESS(4),
    ACADEMY_REPRESENTATIVE(5),
    ACADEMY_CONTACT(6),
    COURSE_LINE(7),
    COURSE_SUBJECT(8),
    COURSE_CURRICULUM(9),
    COURSE_CAPACITY(10),
    COURSE_DURATION(11),
    COURSE_TIME(12),
    COURSE_BASIC_FEE(13),
    COURSE_TEST_FEE(14),
    COURSE_MATERIAL_FEE(15),
    ACADEMY_FOOD_FEE(16),
    ACADEMY_ACCOMMODATION_FEE(17),
    ACADEMY_SHUTTLE_FEE(18),
    ACADEMY_CLOTHES_FEE(19),
    COURSE_TOTAL_FEE(20),
    ACADEMY_TEACHER_COUNT(21);

    private final int index;

    AcademyDataColumnIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
