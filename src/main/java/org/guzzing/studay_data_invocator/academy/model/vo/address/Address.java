package org.guzzing.studay_data_invocator.academy.model.vo.address;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@Embeddable
public class Address {

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "upmyeondong", nullable = false)
    private String upmyeondong;

    public Address(final String address) {
        Assert.isTrue(StringUtils.isNotBlank(address), "주소 정보는 반드시 주어져야 합니다.");
        this.fullAddress = address;

        String[] parsedAddress = parseAddress(address);
        this.sido = parsedAddress[0];
        this.sigungu = parsedAddress[1];
        this.upmyeondong = parsedAddress[2];
    }

    protected Address() {
    }

    private String[] parseAddress(final String address) {
        return address.split(" ");
    }

}
