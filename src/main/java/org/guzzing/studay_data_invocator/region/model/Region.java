package org.guzzing.studay_data_invocator.region.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Entity
@Table(name = "regions")
public class Region {

    @Id
    @Column(name = "code", nullable = false)
    private Long id;

    @Embedded
    private Address address;

    @Column(name = "point", nullable = false)
    private Point point;

    @Column(name = "area", nullable = true)
    private MultiPolygon area;

    public Region(Long id, Address address, Point point, MultiPolygon area) {
        this.id = id;
        this.address = address;
        this.point = point;
        this.area = area;
    }

    public static Region of(Area area, Address address, Point point) {
        return new Region(area.code(), address, point, area.geometry());
    }

    public String getSido() {
        return this.address.getSido();
    }

    public String getSigungu() {
        return this.address.getSigungu();
    }

    public String getUpmyeondong() {
        return this.address.getUpmyeondong();
    }
}
