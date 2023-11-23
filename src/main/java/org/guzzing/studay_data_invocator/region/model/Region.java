package org.guzzing.studay_data_invocator.region.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import org.guzzing.studay_data_invocator.global.location.Location;
import org.guzzing.studay_data_invocator.region.model.vo.Beopjeongdong;
import org.locationtech.jts.geom.MultiPolygon;

@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Beopjeongdong beopjeongdong;

    @Embedded
    private Location location;

    @Column(name = "geometry", nullable = true)
    private MultiPolygon geometry;

    public Region(Beopjeongdong beopjeongdong, Location location, MultiPolygon geometry) {
        this.beopjeongdong = beopjeongdong;
        this.location = location;
        this.geometry = geometry;
    }

}
