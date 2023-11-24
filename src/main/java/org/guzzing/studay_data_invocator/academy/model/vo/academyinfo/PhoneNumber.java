package org.guzzing.studay_data_invocator.academy.model.vo.academyinfo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
@Embeddable
public class PhoneNumber {

    @Transient
    private final String REGEX = "^\\d{2,3}-\\d{3,4}-\\d{3,4}$";

    @Column(name = "phone_number", nullable = true)
    private String phone_number;

    public PhoneNumber(final String phone_number) {
        validate(phone_number);
        this.phone_number = phone_number;
    }

    protected PhoneNumber() {
    }

    private void validate(final String contact) {
        if (!contact.isBlank()) {
            if (!Pattern.matches(REGEX, contact)) {
                throw new IllegalArgumentException("올바른 전화번호 형식이 아닙니다.");
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PhoneNumber that = (PhoneNumber) o;
        return Objects.equals(REGEX, that.REGEX) && Objects.equals(phone_number, that.phone_number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(REGEX, phone_number);
    }

}