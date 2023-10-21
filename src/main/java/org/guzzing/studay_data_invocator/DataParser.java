package org.guzzing.studay_data_invocator;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class DataParser {

    private final DataFileReader dataFileReader;

    public DataParser(DataFileReader dataFileReader) {
        this.dataFileReader = dataFileReader;
    }

    public List<List<String>> parseData(final String fileName) throws IOException {
        List<String> filteredData = getEscapeFilteredData(fileName);

        return filteredData.stream()
                .map(dataLine -> {
                    String[] splitData = dataLine.split(",");
                    return Arrays.stream(DataColumnIndex.values())
                            .map(dataColumn -> splitData[dataColumn.getOriginColumnIndex()])
                            .toList();
                }).toList();
    }

    private List<String> getEscapeFilteredData(String fileName) throws IOException {
        return dataFileReader.readFileData(fileName)
                .stream()
                .filter(dataLine -> Arrays.stream(EscapeToken.values())
                        .noneMatch(token -> dataLine.contains(token.getValue())))
                .toList();
    }

}
