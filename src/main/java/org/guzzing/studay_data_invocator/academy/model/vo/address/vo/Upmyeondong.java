package org.guzzing.studay_data_invocator.academy.model.vo.address.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

@Embeddable
public class Upmyeondong {

    @Column(name = "upmyeondong", nullable = false)
    private String value;

    public Upmyeondong(final String value) {
        validate(value);
        this.value = value;
    }

    protected Upmyeondong() {
    }

    private void validate(final String value) {
        Assert.isTrue(StringUtils.isNotBlank(value), "읍면동 정보는 반드시 주어져야 합니다.");
        Assert.isTrue(value.contains("읍") || value.contains("면") || value.contains("동"), "읍면동 이외의 지역 구분이 포함되었습니다.");
    }

    public String getValue() {
        return value;
    }
}
