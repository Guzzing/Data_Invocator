package org.guzzing.studay_data_invocator.academy.model.vo;

import static org.guzzing.studay_data_invocator.academy.model.vo.address.RegionUnit.SIDO;
import static org.guzzing.studay_data_invocator.academy.model.vo.address.RegionUnit.SIGUNGU;
import static org.guzzing.studay_data_invocator.academy.model.vo.address.RegionUnit.UPMYEONDONG;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import org.guzzing.studay_data_invocator.academy.model.vo.address.RegionUnit;
import org.springframework.util.Assert;

import java.util.Objects;

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
        validateParser(address);
        String[] parsedAddress = address.split(" ");

        this.fullAddress = address;
        this.sido = makeSido(parsedAddress[0]);
        this.sigungu = makeSigungu(parsedAddress[1]);
        this.beopjungdong = makeBeopjungdong(parsedAddress[2]);
    }

    protected Address() {
    }

    public static Address of(final String address) {
        return new Address(address);
    }

    private String makeSido(final String sido) {
        return validateAndReturn(sido, SIDO);
    }

    private String makeSigungu(final String sigungu) {
        return validateAndReturn(sigungu, SIGUNGU);
    }

    private String makeBeopjungdong(final String beopjungdong) {
        return validateAndReturn(beopjungdong, UPMYEONDONG);
    }

    private String validateAndReturn(final String input, final RegionUnit regionUnit) {
        if (StringUtils.isBlank(input) || !regionUnit.isMatched(input)) {
            throw new IllegalArgumentException("올바르지 않은 " + regionUnit + " 구분입니다.");
        }
        return input;
    }

    private void validateParser(String address) {
        if (address.split(" ").length < 3) {
            throw new IllegalArgumentException("주소 정보는 최소한 시도, 시군구, 읍면동 세 가지 요소를 가져야 합니다.");
        }
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
