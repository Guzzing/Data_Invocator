package org.guzzing.studay_data_invocator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class DataFileReader {

    private static final int START_LINE = 5;

    public List<String> readFileData(final String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        List<String> data = new ArrayList<>();

        passFileStartLine(reader);

        String line;
        while ((line = reader.readLine()) != null) {
            data.add(line);
        }

        reader.close();

        return data;
    }

    private void passFileStartLine(BufferedReader reader) throws IOException {
        for (int i = 0; i < START_LINE; i++) {
            reader.readLine();
        }
    }

}
