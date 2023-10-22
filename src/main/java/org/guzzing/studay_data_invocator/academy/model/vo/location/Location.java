package org.guzzing.studay_data_invocator.academy.model.vo.location;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Location {

    @Column(name = "latitude", nullable = false)
    private double latitude;
    @Column(name = "longitude", nullable = false)
    private double longitude;

    protected Location(final double latitude, final double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Location() {
    }

    public static Location of(final double latitude, final double longitude) {
        return new Location(latitude, longitude);
    }
}
