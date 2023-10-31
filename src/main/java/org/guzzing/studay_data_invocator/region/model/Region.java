package org.guzzing.studay_data_invocator.region.model;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.guzzing.studay_data_invocator.global.location.Location;
import org.guzzing.studay_data_invocator.region.model.vo.Address;

@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Address address;

    @Embedded
    private Location location;

    protected Region() {
    }

    protected Region(Address address, Location location) {
        this.address = address;
        this.location = location;
    }

    public static Region of(final Address address, final Location location) {
        return new Region(address, location);
    }

}
