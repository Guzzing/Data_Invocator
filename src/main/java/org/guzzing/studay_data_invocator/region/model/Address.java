package org.guzzing.studay_data_invocator.region.model;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class Address {

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "upmyeondong", nullable = false)
    private String upmyeondong;

    public Address(String sido, String sigungu, String upmyeondong) {
        this.sido = sido;
        this.sigungu = sigungu;
        this.upmyeondong = upmyeondong;
    }
}
