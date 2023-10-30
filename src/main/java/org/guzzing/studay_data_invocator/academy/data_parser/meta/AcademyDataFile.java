package org.guzzing.studay_data_invocator.academy.data_parser.meta;

import java.text.MessageFormat;
import lombok.Getter;

@Getter
public enum AcademyDataFile {
    SEONGNAM("성남");
    //SUWON("수원");

    private static final String PREFIX = "docs/data/";
    private static final String POSTFIX = "-표 1.csv";

    private final String region;

    AcademyDataFile(String region) {
        this.region = region;
    }

    public String getFileName() {
        return MessageFormat.format("{0}{1}{2}", PREFIX, region, POSTFIX);
    }
}
