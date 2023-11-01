package org.guzzing.studay_data_invocator.academy.model.vo;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Objects;
import lombok.Getter;
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

    private String areaOfExpertise;

    protected AcademyInfo(final String name, final String contact, final String shuttle, final String areaOfExpertise) {
        Assert.isTrue(StringUtils.isNotBlank(name), "학원명이 주어지지 않았습니다.");

        this.name = name;
        this.contact = new PhoneNumber(contact);
        this.shuttle = ShuttleAvailability.getShuttleAvailability(shuttle);
        this.areaOfExpertise = areaOfExpertise;
    }

    public static AcademyInfo of(final String name, final String contact, final String shuttle,
            final String areaOfExpertise) {
        return new AcademyInfo(name, contact, shuttle, areaOfExpertise);
    }

    protected AcademyInfo() {
    }

    public String getContact() {
        return contact.getContact();
    }

    public String getShuttle() {
        return shuttle.name();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AcademyInfo that = (AcademyInfo) o;
        return Objects.equals(name, that.name) && Objects.equals(contact, that.contact) && shuttle == that.shuttle;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, contact, shuttle);
    }

}
