package org.guzzing.studay_data_invocator.academy.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.guzzing.studay_data_invocator.academy.model.vo.academy_info.AcademyInfo;
import org.guzzing.studay_data_invocator.academy.model.vo.address.Address;
import org.guzzing.studay_data_invocator.academy.model.vo.class_info.Course;
import org.guzzing.studay_data_invocator.academy.model.vo.location.Location;
import org.guzzing.studay_data_invocator.global.entity.BaseEntity;

@Entity
@Table(name = "academies")
public class Academy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded // Todo Academy + Class 테이블로 정규화 @ElementCollection
    private AcademyInfo academyInfo;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    @Embedded
    private Course course;

    public Academy(
            final AcademyInfo academyInfo,
            final Address address,
            final Location location,
            final Course course
    ) {
        this.academyInfo = academyInfo;
        this.address = address;
        this.location = location;
        this.course = course;
    }

    protected Academy() {
    }

    public String getFullAddress() {
        return this.address.getFullAddress();
    }
}
