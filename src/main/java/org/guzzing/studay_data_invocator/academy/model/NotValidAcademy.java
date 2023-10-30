package org.guzzing.studay_data_invocator.academy.model;


import jakarta.persistence.*;
import org.guzzing.studay_data_invocator.academy.model.vo.AcademyInfo;
import org.guzzing.studay_data_invocator.academy.model.vo.Address;
import org.guzzing.studay_data_invocator.academy.model.vo.Location;

@Entity
@Table(name = "notValidAcademies")
public class NotValidAcademy implements Institute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Embedded
    private AcademyInfo academyInfo;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    protected NotValidAcademy(
            final AcademyInfo academyInfo,
            final Address address,
            final Location location
    ) {
        this.academyInfo = academyInfo;
        this.address = address;
        this.location = location;
    }

    protected NotValidAcademy() {
    }

    public static NotValidAcademy of(final AcademyInfo academyInfo, final Address address, final Location location) {
        return new NotValidAcademy(academyInfo, address, location);
    }

    public String getFullAddress() {
        return this.address.getFullAddress();
    }

}
