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

    public Location(final String address) {
        // todo 지오코딩 모듈 넣기
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Location() {
    }
}
