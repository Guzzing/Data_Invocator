package org.guzzing.studay_data_invocator.sourceacademy.util;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.binary.XSSFBSheetHandler;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Getter
@Setter
public class ExcelSheetHandler implements XSSFBSheetHandler.SheetContentsHandler {

    private static final int START_ROW = 4;
    private int currentCol = -1;
    private int currRowNum = 0;
    private List<List<String>> rows = new ArrayList<>();
    private List<String> row = new ArrayList<>();
    private List<String> header = new ArrayList<>();

    public static List<ExcelSheetHandler> readExcel(File file) throws Exception {
        List<ExcelSheetHandler> sheetHandlers = new ArrayList<>();
        try {

            OPCPackage opc = OPCPackage.open(file);
            XSSFReader xssfReader = new XSSFReader(opc);
            StylesTable styles = xssfReader.getStylesTable();
            Iterator<InputStream> sheets = xssfReader.getSheetsData();
            ReadOnlySharedStringsTable strings = new ReadOnlySharedStringsTable(opc);

            XSSFReader.SheetIterator sheetIterator = (XSSFReader.SheetIterator) sheets;
            while (sheetIterator.hasNext()) {
                ExcelSheetHandler sheetHandler = new ExcelSheetHandler();
                InputStream inputStream = sheetIterator.next();

                InputSource inputSource = new InputSource(inputStream);

                DataFormatter dataFormatter = new DataFormatter();
                dataFormatter.addFormat("General", new java.text.DecimalFormat("#.###############"));
                ContentHandler handle = new XSSFSheetXMLHandler(styles, strings, sheetHandler, dataFormatter, false);

                SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
                saxParserFactory.setNamespaceAware(true);
                SAXParser parser = saxParserFactory.newSAXParser();
                XMLReader xmlReader = parser.getXMLReader();
                xmlReader.setContentHandler(handle);
                xmlReader.parse(inputSource);

                inputStream.close();
                sheetHandlers.add(sheetHandler);
            }
            opc.close();
        } catch (Exception e) {

        }

        return sheetHandlers;

    }

    public List<List<String>> getRows() {
        return rows;
    }

    @Override
    public void startRow(int arg0) {
        this.currentCol = -1;
        this.currRowNum = arg0;
    }

    @Override
    public void cell(String columnName, String value, XSSFComment var3) {
        int iCol = (new CellReference(columnName)).getCol();
        int emptyCol = iCol - currentCol - 1;

        for (int i = 0; i < emptyCol; i++) {
            row.add("");
        }
        currentCol = iCol;
        row.add(value);
    }

    @Override
    public void headerFooter(String arg0, boolean arg1, String arg2) {

    }

    @Override
    public void endRow(int rowNum) {
        if (rowNum >= START_ROW) {
            if (rowNum == START_ROW) {
                header = new ArrayList<>(row);
            } else {
                if (row.size() < header.size()) {
                    for (int i = row.size(); i < header.size(); i++) {
                        row.add("");
                    }
                }
                rows.add(new ArrayList<>(row));
            }
        }
        row.clear();
    }

    public void hyperlinkCell(String arg0, String arg1, String arg2, String arg3, XSSFComment arg4) {

    }

}
