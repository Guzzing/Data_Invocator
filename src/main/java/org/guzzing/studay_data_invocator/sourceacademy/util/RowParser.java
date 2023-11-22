package org.guzzing.studay_data_invocator.sourceacademy.util;

import org.guzzing.studay_data_invocator.sourceacademy.model.SourceAcademy;

import java.io.File;
import java.util.List;

public class RowParser {

    public static List<List<SourceAcademy>> parser(String fileLocation) throws Exception {
        File file = new File(fileLocation);

        return ExcelSheetHandler
                .readExcel(file).stream()
                .map(excelSheetHandler ->
                        excelSheetHandler.getRows()
                                .stream()
                                .map(row -> CellParser.parseCell(row))
                                .toList()
                ).toList();
    }

}
