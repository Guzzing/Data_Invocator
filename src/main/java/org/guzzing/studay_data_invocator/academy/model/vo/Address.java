package org.guzzing.studay_data_invocator.academy.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.guzzing.studay_data_invocator.academy.model.vo.address.AddressParser;
import org.guzzing.studay_data_invocator.academy.model.vo.address.RegionUnit;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.guzzing.studay_data_invocator.academy.model.vo.address.RegionUnit.*;

@Getter
@Embeddable
public class Address {

    @Column(name = "full_address", nullable = false)
    private String fullAddress;

    @Column(name = "sido", nullable = false)
    private String sido;

    @Column(name = "sigungu", nullable = false)
    private String sigungu;

    @Column(name = "beopjungdong", nullable = false)
    private String beopjungdong;

    protected Address(final String address) {
        Assert.isTrue(StringUtils.isNotBlank(address), "주소 정보는 반드시 주어져야 합니다.");
        this.fullAddress = address;

        List<String> parsedAddress = AddressParser.parseAddress(fullAddress);
        this.sido = parsedAddress.get(0);
        this.sigungu = parsedAddress.get(1);
        this.beopjungdong = parsedAddress.get(2);
    }

    protected Address() { }

    public static Address of(final String address) {
        return new Address(address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(fullAddress, address.fullAddress) && Objects.equals(sido, address.sido) && Objects.equals(sigungu, address.sigungu) && Objects.equals(beopjungdong, address.beopjungdong);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullAddress, sido, sigungu, beopjungdong);
    }

}


