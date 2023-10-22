package org.guzzing.studay_data_invocator.global.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import org.guzzing.studay_data_invocator.global.exception.DataFileException;
import org.springframework.stereotype.Component;

@Component
public class DataFileReader {

    private static final int IGNORE_LINE_COUNT = 5;

    public List<String> readFileData(final String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            passFileStartLine(reader);

            return reader.lines()
                    .toList();
        } catch (IOException e) {
            throw new DataFileException(e);
        }
    }

    private void passFileStartLine(BufferedReader reader) {
        try {
            for (int i = 0; i < IGNORE_LINE_COUNT; i++) {
                reader.readLine();
            }
        } catch (IOException e) {
            throw new DataFileException(e);
        }
    }

}
