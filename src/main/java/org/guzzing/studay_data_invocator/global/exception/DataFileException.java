package org.guzzing.studay_data_invocator.global.exception;

public class DataFileException extends RuntimeException {

    public DataFileException(final Exception e) {
        super(e);
    }
}
