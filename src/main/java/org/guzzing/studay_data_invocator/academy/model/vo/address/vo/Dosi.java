package org.guzzing.studay_data_invocator.academy.model.vo.address.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

@Embeddable
public class Dosi {

    @Column(name = "dosi", nullable = false)
    private String value;

    public Dosi(final String value) {
        validate(value);
        this.value = value;
    }

    protected Dosi() {
    }

    private void validate(final String value) {
        Assert.isTrue(StringUtils.isNotBlank(value), "광역 정보는 반드시 주어져야 합니다.");
        Assert.isTrue(value.contains("도") || value.contains("시"), "광역 정보에는 도 혹은 시 단위여야 합니다.");
    }

    public String getValue() {
        return value;
    }
}
