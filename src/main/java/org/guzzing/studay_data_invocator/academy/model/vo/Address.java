package org.guzzing.studay_data_invocator.academy.model.vo;

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

    @Column(name = "beopjungdong", nullable = false)
    private String beopjungdong;

    protected Address(final String address) {
        Assert.isTrue(StringUtils.isNotBlank(address), "주소 정보는 반드시 주어져야 합니다.");
        this.fullAddress = address;

        String[] parsedAddress = parseAddress(address);
        this.sido = parsedAddress[0];
        this.sigungu = parsedAddress[1];
        this.beopjungdong = parsedAddress[2];

        validate(sido, sigungu, beopjungdong);
    }

    protected Address() {
    }

    public static Address of(final String address) {
        return new Address(address);
    }

    private String[] parseAddress(final String address) {
        return address.split(" ");
    }

    private void validate(final String sido, final String sigungu, final String beopjungdong) {
        Assert.isTrue(
                (sido.contains("시") || sido.contains("도")),
                "올바르지 않은 시도 구분입니다.");
        Assert.isTrue(
                (sigungu.contains("시") || sigungu.contains("군") || sigungu.contains("구")),
                "올바르지 않은 시군구 구분입니다.");
        Assert.isTrue(
                (beopjungdong.contains("읍") || beopjungdong.contains("면") || beopjungdong.contains("동")
                        || beopjungdong.contains("구")),
                "올바르지 않은 읍면동 구분입니다.");
    }

}
