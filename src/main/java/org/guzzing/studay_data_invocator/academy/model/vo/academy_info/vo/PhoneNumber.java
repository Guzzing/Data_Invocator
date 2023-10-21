package org.guzzing.studay_data_invocator.academy.model.vo.academy_info.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import java.util.regex.Pattern;
import org.springframework.util.Assert;

@Embeddable
public class PhoneNumber {

    @Transient
    private final String REGEX = "^\\d{3}-\\d{3,4}-\\d{4}$";

    @Column(name = "contact", nullable = true, length = 20)
    private String contact;

    public PhoneNumber(final String contact) {
        validate(contact);
        this.contact = contact;
    }

    protected PhoneNumber() {
    }

    private void validate(final String contact) {
        if (contact != null || !contact.isBlank()) {
            Assert.isTrue(Pattern.matches(REGEX, contact), "올바른 전화번호 형식이 아닙니다.");
        }
    }

    public String getContact() {
        return contact;
    }
}
