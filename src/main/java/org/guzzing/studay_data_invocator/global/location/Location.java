package org.guzzing.studay_data_invocator.global.location;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Double.compare(latitude, location.latitude) == 0
                && Double.compare(longitude, location.longitude) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}
