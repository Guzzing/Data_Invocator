package org.guzzing.studay_data_invocator.academy.data_parser;

public enum EscapeToken {
    TECH_JOB("직업기술");

    private final String value;

    EscapeToken(final String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isEscapeToken(String input) {
        for (EscapeToken token : EscapeToken.values()) {
            if (token.equals(input)) {
                return false;
            }
        }

        return true;
    }

}
