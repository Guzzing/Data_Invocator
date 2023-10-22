package org.guzzing.studay_data_invocator.academy.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import org.guzzing.studay_data_invocator.academy.model.vo.academy_info.PhoneNumber;
import org.guzzing.studay_data_invocator.academy.model.vo.academy_info.ShuttleAvailability;
import org.springframework.util.Assert;

@Embeddable
public class AcademyInfo {

    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @Embedded
    private PhoneNumber contact;

    @Enumerated(value = EnumType.STRING)
    private ShuttleAvailability shuttle;

    protected AcademyInfo(final String name, final String contact, final String shuttle) {
        Assert.isTrue(StringUtils.isNotBlank(name), "학원명이 주어지지 않았습니다.");

        this.name = name;
        this.contact = new PhoneNumber(contact);
        this.shuttle = ShuttleAvailability.getShuttleAvailability(shuttle);
    }

    public static AcademyInfo of(final String name, final String contact, final String shuttle) {
        return new AcademyInfo(name, contact, shuttle);
    }

    protected AcademyInfo() {
    }

    public String getContact() {
        return contact.getContact();
    }

    public String getShuttle() {
        return shuttle.name();
    }
}
