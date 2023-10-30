package org.guzzing.studay_data_invocator.academy.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import java.util.regex.Pattern;
import lombok.Getter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Getter
@Embeddable
public class PhoneNumber {

    @Transient
    private final String REGEX = "^\\d{2,3}-\\d{3,4}-\\d{3,4}$";

    @Column(name = "contact", nullable = true)
    private String contact;

    public PhoneNumber(final String contact) {
        validate(contact);
        this.contact = contact;
    }

    protected PhoneNumber() {
    }

    private void validate(final String contact) {
        if(!contact.isBlank()) {
            if (!Pattern.matches(REGEX, contact)) {
                throw new IllegalArgumentException("올바른 전화번호 형식이 아닙니다.");
            }
        }
    }

    private String regulate(final String contact) {
        return contact.isBlank() ? null : contact;
    }

}
