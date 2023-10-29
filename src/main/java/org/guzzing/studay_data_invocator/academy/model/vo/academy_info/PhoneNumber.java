package org.guzzing.studay_data_invocator.academy.model.vo.academy_info;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import java.util.regex.Pattern;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
@Embeddable
public class PhoneNumber {

    @Transient
    private final String REGEX = "^\\d{2,3}-\\d{3,4}-\\d{3,4}$";

    @Column(name = "contact", nullable = true)
    private String contact;

    public PhoneNumber(final String contact) {
        this.contact =  validate(contact)? regulate(contact) :  "잘못된 형식: "+contact;
    }

    protected PhoneNumber() {
    }

    private boolean validate(final String contact) {
        return !contact.isBlank() && Pattern.matches(REGEX, contact) ;

    }

    private String regulate(final String contact) {
        return contact.isBlank() ? null : contact;
    }
}
