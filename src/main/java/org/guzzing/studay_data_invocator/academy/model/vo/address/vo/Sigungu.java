package org.guzzing.studay_data_invocator.academy.model.vo.address.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

@Embeddable
public class Sigungu {

    @Column(name = "sigungu", nullable = false)
    private String value;

    public Sigungu(final String value) {
        validate(value);
        this.value = value;
    }

    protected Sigungu() {
    }

    private void validate(final String value) {
        Assert.isTrue(StringUtils.isNotBlank(value), "시군구 정보는 반드시 주어져야 합니다.");
        Assert.isTrue(value.contains("시") || value.contains("군") || value.contains("구"), "시군구 이외의 지역 구분이 포함되었습니다.");
    }

    public String getValue() {
        return value;
    }
}
