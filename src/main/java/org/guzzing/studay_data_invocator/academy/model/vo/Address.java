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
        this.fullAddress = StringUtils.isNotBlank(address) ? address : "주소 정보는 반드시 주어져야 합니다. " + address;

        String[] notValidAddress = address.split(" ");
        String[] parsedAddress = notValidAddress.length >= 3 ? notValidAddress : "시도 시군구 읍면동".split(" ");

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
        return validateAndReturn(sido, "시도");
    }

    private String makeSigungu(final String sigungu) {
        return validateAndReturn(sigungu, "시군구");
    }

    private String makeBeopjungdong(final String beopjungdong) {
        return validateAndReturn(beopjungdong, "읍면동");
    }

    private String validateAndReturn(final String input, final String type) {
        if (StringUtils.isBlank(input) || !isValidType(input, type)) {
            return "올바르지 않은 " + type + " 구분입니다.";
        }
        return input;
    }

    private boolean isValidType(final String input, final String type) {
        switch (type) {
            case "시도":
                return input.contains("시") || input.contains("도");
            case "시군구":
                return input.contains("시") || input.contains("군") || input.contains("구");
            case "읍면동":
                return input.contains("읍") || input.contains("면") || input.contains("동") || input.contains("구");
            default:
                return false;
        }
    }
}
