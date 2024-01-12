package org.guzzing.studay_data_invocator.academy.model;

import jakarta.persistence.*;

import java.util.Objects;

import lombok.Getter;
import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.source.SeoulSourceAcademy;
import org.guzzing.studay_data_invocator.academy.model.vo.AcademyInfo;
import org.guzzing.studay_data_invocator.academy.model.vo.Address;
import org.guzzing.studay_data_invocator.academy.model.vo.Location;
import org.guzzing.studay_data_invocator.academy.model.vo.info.PhoneNumber;
import org.guzzing.studay_data_invocator.academy.model.vo.info.ShuttleAvailability;
import org.guzzing.studay_data_invocator.global.entity.BaseEntity;
import org.locationtech.jts.geom.Point;

@Getter
@Entity
@Table(name = "academies")
public class Academy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AcademyInfo academyInfo;

    @Embedded
    private Address fullAddress;

    @Embedded
    private Location location;

    @Column(name = "max_education_fee")
    private Long maxEducationFee;

    @Column(name = "point")
    private Point point;

    @Column(name="source_academy_identifier", nullable = false)
    private Long sourceAcademyIdentifier;

    protected Academy(
            final AcademyInfo academyInfo,
            final Address fullAddress,
            final Location location
    ) {
        this.academyInfo = academyInfo;
        this.fullAddress = fullAddress;
        this.location = location;
    }

    protected Academy(
            final Long sourceAcademyIdentifier,
            final String academyName,
            final String phoneNumber,
            final String shuttle,
            final String areaOfExpertise,
            final String fullAddress,
            final Location location,
            final Point point
    ) {
        this.sourceAcademyIdentifier =sourceAcademyIdentifier;
        this.academyInfo = AcademyInfo.of(
                academyName,
                phoneNumber,
                shuttle,
                areaOfExpertise);
        this.fullAddress = Address.of(fullAddress);
        this.location = location;
        this.point = point;
    }

    protected Academy() {
    }

    public static Academy of(final AcademyInfo academyInfo, final Address address, final Location location) {
        return new Academy(academyInfo, address, location);
    }

    public void changeEducationFee(Long maxEducationFee) {
        this.maxEducationFee = maxEducationFee ==null ? 0L : maxEducationFee;
    }

    public String getFullAddress() {
        return fullAddress.getFullAddress();
    }

    public String getAcademyName() {
        return academyInfo.getAcademyName();
    }

    public String getContact() {
        return academyInfo.getContact();
    }

    public String getShuttleAvailability() {
        return academyInfo.getShuttle().toString();
    }

    public String getAreaOfExpertise() {
        return academyInfo.getAreaOfExpertise();
    }

    public void changePoint(Point point) {
        this.point = point;
    }

    public static Academy to(
            GyeonggiSourceAcademy gyeonggiSourceAcademy,
            Location location,
            Point point) {
        return new Academy(
                Long.valueOf(gyeonggiSourceAcademy.getHashCodeValue()),
                gyeonggiSourceAcademy.getAcademyName(),
                gyeonggiSourceAcademy.getContact(),
                String.valueOf(gyeonggiSourceAcademy.getShuttleFee()),
                gyeonggiSourceAcademy.getAreaOfExpert(),
                gyeonggiSourceAcademy.getAddress(),
                location,
                point
        );
    }

    public static Academy to(
            SeoulSourceAcademy seoulSourceAcademy,
            Location location,
            Point point) {
        return new Academy(
                Long.valueOf(seoulSourceAcademy.getAcademyDesignationNumber()),
                seoulSourceAcademy.getAcademyName(),
                " ",
                String.valueOf(0),
                seoulSourceAcademy.getNameOfField(),
                seoulSourceAcademy.getAddress()+seoulSourceAcademy.getDetailAddress(),
                location,
                point
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Academy academy = (Academy) o;
        return Objects.equals(academyInfo, academy.academyInfo) && Objects.equals(fullAddress, academy.fullAddress) && Objects.equals(location, academy.location) && Objects.equals(maxEducationFee, academy.maxEducationFee) && Objects.equals(point, academy.point) && Objects.equals(sourceAcademyIdentifier, academy.sourceAcademyIdentifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(academyInfo, fullAddress, location, maxEducationFee, point, sourceAcademyIdentifier);
    }

}
