package org.guzzing.studay_data_invocator.academy.infra.xlsx;

import org.guzzing.studay_data_invocator.academy.model.source.GyeonggiSourceAcademy;

import java.io.File;
import java.util.List;

public class RowParser {

    public static List<List<GyeonggiSourceAcademy>> parser(String fileLocation) throws Exception {
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
