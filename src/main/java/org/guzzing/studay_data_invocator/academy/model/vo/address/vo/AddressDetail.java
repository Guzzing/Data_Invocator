package org.guzzing.studay_data_invocator.academy.model.vo.address.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

@Embeddable
public class AddressDetail {

    @Column(name = "address", nullable = false)
    private String value;

    public AddressDetail(final String value) {
        validate(value);
        this.value = value;
    }

    protected AddressDetail() {
    }

    private void validate(final String value) {
        Assert.isTrue(StringUtils.isNotBlank(value), "주소 정보가 주어지지 않았습니다.");
    }

    public String[] parseAddressData() {
        return this.value.split(" ");
    }

    public String getValue() {
        return value;
    }
}
