package org.guzzing.studay_data_invocator.sourceacademy.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.guzzing.studay_data_invocator.sourceacademy.infra.api.dto.SeoulAcademyInfo;

@Getter
@Table(name = "seoul_source_academies")
@Entity
public class SeoulSourceAcademy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "region_name")
    private String regionName;

    @Column(name = "academy_institution_status")
    private String academyInstitutionStatus;

    @Column(name = "academy_designation_number")
    private String academyDesignationNumber;

    @Column(name = "academy_name")
    private String academyName;

    @Column(name = "establishment_date")
    private String establishmentDate;

    @Column(name = "registration_date")
    private String registrationDate;

    @Column(name = "registration_status")
    private String registrationStatus;

    @Column(name = "closed_begin_date")
    private String closedBeginDate;

    @Column(name = "closed_end_date")
    private String closedEndDate;

    @Column(name = "total_capacity")
    private Long totalCapacity;

    @Column(name = "total_temporary_capacity")
    private Long totalTemporaryCapacity;

    @Column(name = "name_of_field")
    private String nameOfField;

    @Column(name = "teaching_division_name")
    private String teachingDivisionName;

    @Column(name = "instructional_course_list_name")
    private String instructionalCourseListName;

    @Column(name = "name_of_teaching_course")
    private String nameOfTeachingCourse;

    @Column(name = "contents_of_course_fee_per_person", columnDefinition = "TEXT")
    private String contentsOfCourseFeePerPerson;

    @Column(name = "tuition_disclosure_status")
    private Boolean tuitionDisclosureStatus;

    @Column(name = "dormitory_status")
    private Boolean dormitoryStatus;

    @Column(name = "road_name_zip_code")
    private String roadNameZipCode;

    @Column(name = "address")
    private String address;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "load_date")
    private String loadDate;

    protected SeoulSourceAcademy() {

    }

    private SeoulSourceAcademy(
            String regionName,
            String academyInstitutionStatus,
            String academyDesignationNumber,
            String academyName,
            String establishmentDate,
            String registrationDate,
            String registrationStatus,
            String closedBeginDate,
            String closedEndDate,
            Long totalCapacity,
            Long totalTemporaryCapacity,
            String nameOfField,
            String teachingDivisionName,
            String instructionalCourseListName,
            String nameOfTeachingCourse,
            String contentsOfCourseFeePerPerson,
            Boolean tuitionDisclosureStatus,
            Boolean dormitoryStatus,
            String roadNameZIPCode,
            String address,
            String detailAddress,
            String loadDate) {
        this.regionName = regionName;
        this.academyInstitutionStatus = academyInstitutionStatus;
        this.academyDesignationNumber = academyDesignationNumber;
        this.academyName = academyName;
        this.establishmentDate = establishmentDate;
        this.registrationDate = registrationDate;
        this.registrationStatus = registrationStatus;
        this.closedBeginDate = closedBeginDate;
        this.closedEndDate = closedEndDate;
        this.totalCapacity = totalCapacity;
        this.totalTemporaryCapacity = totalTemporaryCapacity;
        this.nameOfField = nameOfField;
        this.teachingDivisionName = teachingDivisionName;
        this.instructionalCourseListName = instructionalCourseListName;
        this.nameOfTeachingCourse = nameOfTeachingCourse;
        this.contentsOfCourseFeePerPerson = contentsOfCourseFeePerPerson;
        this.tuitionDisclosureStatus = tuitionDisclosureStatus;
        this.dormitoryStatus = dormitoryStatus;
        this.roadNameZipCode = roadNameZIPCode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.loadDate = loadDate;
    }

    public static SeoulSourceAcademy of(
            String regionName,
            String academyInstitutionStatus,
            String academyDesignationNumber,
            String academyName,
            String establishmentDate,
            String registrationDate,
            String registrationStatus,
            String closedBeginDate,
            String closedEndDate,
            Long totalCapacity,
            Long totalTemporaryCapacity,
            String nameOfField,
            String teachingDivisionName,
            String instructionalCourseListName,
            String nameOfTeachingCourse,
            String contentsOfCourseFeePerPerson,
            Boolean tuitionDisclosureStatus,
            Boolean dormitoryStatus,
            String roadNameZIPCode,
            String address,
            String detailAddress,
            String loadDate
    ) {
        return new SeoulSourceAcademy(
                regionName,
                academyInstitutionStatus,
                academyDesignationNumber,
                academyName,
                establishmentDate,
                registrationDate,
                registrationStatus,
                closedBeginDate,
                closedEndDate,
                totalCapacity,
                totalTemporaryCapacity,
                nameOfField,
                teachingDivisionName,
                instructionalCourseListName,
                nameOfTeachingCourse,
                contentsOfCourseFeePerPerson,
                tuitionDisclosureStatus,
                dormitoryStatus,
                roadNameZIPCode,
                address,
                detailAddress,
                loadDate
        );
    }

    public static SeoulSourceAcademy to(SeoulAcademyInfo seoulAcademyInfo){
        return new SeoulSourceAcademy(
                seoulAcademyInfo.getRegionName(),
                seoulAcademyInfo.getAcademyInstitutionStatus(),
                seoulAcademyInfo.getAcademyDesignationNumber(),
                seoulAcademyInfo.getAcademyName(),
                seoulAcademyInfo.getEstablishmentDate(),
                seoulAcademyInfo.getRegistrationDate(),
                seoulAcademyInfo.getRegistrationStatus(),
                seoulAcademyInfo.getClosedBeginDate(),
                seoulAcademyInfo.getClosedEndDate(),
                Long.parseLong(seoulAcademyInfo.getTotalCapacity()),
                Long.parseLong(seoulAcademyInfo.getTotalTemporaryCapacity()),
                seoulAcademyInfo.getNameOfField(),
                seoulAcademyInfo.getTeachingDivisionName(),
                seoulAcademyInfo.getInstructionalCourseListName(),
                seoulAcademyInfo.getNameOfTeachingCourse(),
                seoulAcademyInfo.getContentsOfCourseFeePerPerson(),
                isYes(seoulAcademyInfo.getTuitionDisclosureStatus()),
                isYes(seoulAcademyInfo.getDormitoryStatus()),
                seoulAcademyInfo.getRoadNameZipCode(),
                seoulAcademyInfo.getAddress(),
                seoulAcademyInfo.getDetailAddress(),
                seoulAcademyInfo.getLoadDate()
        );
    }

    private static boolean isYes(String value) {
        return "Y".equalsIgnoreCase(value);
    }

}
