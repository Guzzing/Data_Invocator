package org.guzzing.studay_data_invocator.academy.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;
import org.guzzing.studay_data_invocator.academy.model.vo.AcademyInfo;
import org.guzzing.studay_data_invocator.academy.model.vo.Address;
import org.guzzing.studay_data_invocator.global.entity.BaseEntity;
import org.guzzing.studay_data_invocator.global.location.Location;

@Entity
@Table(name = "academies")
public class Academy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private AcademyInfo academyInfo;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    private Long maxEducationFee;

    protected Academy(
            final AcademyInfo academyInfo,
            final Address address,
            final Location location
    ) {
        this.academyInfo = academyInfo;
        this.address = address;
        this.location = location;
    }

    protected Academy() {
    }

    public static Academy of(final AcademyInfo academyInfo, final Address address, final Location location) {
        return new Academy(academyInfo, address, location);
    }

    public void changeEducationFee(Long maxEducationFee) {
        this.maxEducationFee = maxEducationFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Academy academy = (Academy) o;
        return Objects.equals(id, academy.id) && Objects.equals(academyInfo, academy.academyInfo) && Objects.equals(
                address, academy.address) && Objects.equals(location, academy.location) && Objects.equals(
                maxEducationFee, academy.maxEducationFee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, academyInfo, address, location, maxEducationFee);
    }
}
