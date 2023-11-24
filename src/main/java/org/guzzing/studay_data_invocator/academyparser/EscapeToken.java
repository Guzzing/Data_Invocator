package org.guzzing.studay_data_invocator.academy.parser;

import lombok.Getter;

@Getter
public enum EscapeToken {
    TECH_JOB("직업기술");

    private final String value;

    EscapeToken(final String value) {
        this.value = value;
    }

    public static boolean isEscapeToken(final String input) {
        for (EscapeToken token : EscapeToken.values()) {
            if (token.equals(input)) {
                return false;
            }
        }

        return true;
    }

}
