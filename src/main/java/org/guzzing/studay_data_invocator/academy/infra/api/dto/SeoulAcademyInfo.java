package org.guzzing.studay_data_invocator.academy.infra.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SeoulAcademyInfo {

    @JsonProperty("ADMST_ZONE_NM")
    private String regionName;

    @JsonProperty("ACA_INSTI_SC_NM")
    private String academyInstitutionStatus;

    @JsonProperty("ACA_ASNUM")
    private String academyDesignationNumber;

    @JsonProperty("ACA_NM")
    private String academyName;

    @JsonProperty("ESTBL_YMD")
    private String establishmentDate;

    @JsonProperty("REG_YMD")
    private String registrationDate;

    @JsonProperty("REG_STTUS_NM")
    private String registrationStatus;

    @JsonProperty("CAA_BEGIN_YMD")
    private String closedBeginDate;

    @JsonProperty("CAA_END_YMD")
    private String closedEndDate;

    @JsonProperty("TOFOR_SMTOT")
    private String totalCapacity;

    @JsonProperty("DTM_RCPTN_ABLTY_NMPR_SMTOT")
    private String totalTemporaryCapacity;

    @JsonProperty("REALM_SC_NM")
    private String nameOfField;

    @JsonProperty("LE_ORD_NM")
    private String teachingDivisionName;

    @JsonProperty("LE_CRSE_LIST_NM")
    private String instructionalCourseListName;

    @JsonProperty("LE_CRSE_NM")
    private String nameOfTeachingCourse;

    @JsonProperty("PSNBY_THCC_CNTNT")
    private String contentsOfCourseFeePerPerson;

    @JsonProperty("THCC_OTHBC_YN")
    private String tuitionDisclosureStatus;

    @JsonProperty("BRHS_ACA_YN")
    private String dormitoryStatus;

    @JsonProperty("FA_RDNZC")
    private String roadNameZipCode;

    @JsonProperty("FA_RDNMA")
    private String address;

    @JsonProperty("FA_RDNDA")
    private String detailAddress;

    @JsonProperty("LOAD_DTM")
    private String loadDate;


}
