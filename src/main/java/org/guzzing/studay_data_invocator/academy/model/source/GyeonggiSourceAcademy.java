package org.guzzing.studay_data_invocator.academy.model.source;

import jakarta.persistence.*;
import lombok.Getter;
import org.guzzing.studay_data_invocator.global.entity.BaseEntity;

import java.util.Objects;

@Getter
@Entity
@Table(name = "gyeonggi_source_academies")
public class GyeonggiSourceAcademy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "academy_name")
    private String academyName;

    @Column(name = "academy_type")
    private String academyType;

    @Column(name = "area_of_expert")
    private String areaOfExpert;

    @Column(name = "address")
    private String address;

    @Column(name = "representative")
    private String representative;

    @Column(name = "contact")
    private String contact;

    @Column(name = "lesson_line")
    private String lessonLine;

    @Column(name = "lesson_subject")
    private String lessonSubject;

    @Column(name = "lesson_curriculum")
    private String lessonCurriculum;

    @Column(name = "lesson_capacity")
    private long lessonCapacity;

    @Column(name = "lesson_duration")
    private String lessonDuration;

    @Column(name = "teaching_time")
    private long teachingTime;

    @Column(name = "tuition_fee")
    private long tuitionFee;

    @Column(name = "mock_test_fee")
    private long mockTestFee;

    @Column(name = "material_fee")
    private long materialFee;

    @Column(name = "meal_fee")
    private long mealFee;

    @Column(name = "dormitory_fee")
    private long dormitoryFee;

    @Column(name = "shuttle_fee")
    private long shuttleFee;

    @Column(name = "clothing_fee")
    private long clothingFee;

    @Column(name = "total_fee")
    private long totalFee;

    @Column(name = "teacher_count")
    private long teacherCount;

    @Column(name = "hash_code_value")
    private int hashCodeValue;

    protected GyeonggiSourceAcademy() {
    }

    public GyeonggiSourceAcademy(
            String regionName, String academyName, String academyType,
            String areaOfExpert, String address, String representative,
            String contact, String lessonLine, String lessonSubject,
            String lessonCurriculum, long lessonCapacity, String lessonDuration,
            long teachingTime, long tuitionFee, long mockTestFee,
            long materialFee, long mealFee, long dormitoryFee,
            long shuttleFee, long clothingFee, long totalFee,
            long teacherCount
    ) {
        this.regionName = regionName;
        this.academyName = academyName;
        this.academyType = academyType;
        this.areaOfExpert = areaOfExpert;
        this.address = address;
        this.representative = representative;
        this.contact = contact;
        this.lessonLine = lessonLine;
        this.lessonSubject = lessonSubject;
        this.lessonCurriculum = lessonCurriculum;
        this.lessonCapacity = lessonCapacity;
        this.lessonDuration = lessonDuration;
        this.teachingTime = teachingTime;
        this.tuitionFee = tuitionFee;
        this.mockTestFee = mockTestFee;
        this.materialFee = materialFee;
        this.mealFee = mealFee;
        this.dormitoryFee = dormitoryFee;
        this.shuttleFee = shuttleFee;
        this.clothingFee = clothingFee;
        this.totalFee = totalFee;
        this.teacherCount = teacherCount;
    }

    public static GyeonggiSourceAcademy of(
            String regionName, String academyName, String academyType,
            String areaOfExpert, String address, String representative,
            String contact, String lessonLine, String lessonSubject,
            String lessonCurriculum, long lessonCapacity, String lessonDuration,
            long teachingTime, long tuitionFee, long mockTestFee,
            long materialFee, long mealFee, long dormitoryFee,
            long shuttleFee, long clothingFee, long totalFee,
            long teacherCount
    ) {
        return new GyeonggiSourceAcademy(
                regionName,
                academyName,
                academyType,
                areaOfExpert,
                address,
                representative,
                contact,
                lessonLine,
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
                teacherCount);
    }

    public void setHashCodeValue() {
        this.hashCodeValue = hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GyeonggiSourceAcademy that = (GyeonggiSourceAcademy) o;
        return Objects.equals(regionName, that.regionName)
                && Objects.equals(academyName, that.academyName)
                && Objects.equals(address, that.address)
                && Objects.equals(representative, that.representative);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regionName, academyName, address, representative);
    }

}
