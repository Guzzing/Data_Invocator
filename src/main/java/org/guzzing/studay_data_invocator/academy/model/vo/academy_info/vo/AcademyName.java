package org.guzzing.studay_data_invocator.academy.model.vo.academy_info.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

@Embeddable
public class AcademyName {

    @Column(name = "name", nullable = false)
    private String value;

    public AcademyName(final String value) {
        validate(value);
        this.value = value;
    }

    protected AcademyName() {
    }

    private void validate(final String value) {
        Assert.isTrue(StringUtils.isNotBlank(value), "학원명이 주어지지 않았습니다.");
    }

    public String getValue() {
        return value;
    }
}
