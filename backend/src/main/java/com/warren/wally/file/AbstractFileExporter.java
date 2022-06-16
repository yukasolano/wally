package com.warren.wally.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ByteArrayResource;

public abstract class AbstractFileExporter<T> implements FileExporter<T> {

    public ExportedFile export(List<T> dados) {

        Workbook workbook = new XSSFWorkbook();

        /*
         * CreationHelper helps us create instances of various things like DataFormat,
         * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
         */
        CreationHelper createHelper = workbook.getCreationHelper();

        // Create a CellStyle with the font
        CellStyle headerCellStyle = getHeaderStyle(workbook);

        // Create Cell Style for formatting Date
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

        // Create a Sheet
        Sheet sheet = workbook.createSheet(getSheetName());
        // Create a Row
        Row headerRow = sheet.createRow(0);

        // Create cells
        String[] columns = getColumns();
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }

        // Create Other rows and cells with data
        populateCells(dados, sheet, dateCellStyle);

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        return new ExportedFile(getFileName(), writeFile(workbook));
    }

    abstract String getFileName();

    abstract String getSheetName();

    abstract String[] getColumns();

    abstract void populateCells(List<T> dados,
                                Sheet sheet,
                                CellStyle dateCellStyle);

    protected ByteArrayResource writeFile(Workbook workbook) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
            ByteArrayResource resource = new ByteArrayResource(bos.toByteArray());
            bos.close();
            workbook.close();
            return resource;
        } catch (IOException e) {
            return null;
        }
    }

    protected Date convertToDate(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    private CellStyle getHeaderStyle(Workbook workbook) {
        // Create a Font for styling header cells
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 14);

        // Create a CellStyle with the font
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        return headerCellStyle;
    }

    protected void addCellDate(Row row, int position, LocalDate data, CellStyle dateCellStyle) {
        if(data != null) {
            Cell cell = row.createCell(position);
            cell.setCellValue(convertToDate(data));
            cell.setCellStyle(dateCellStyle);
        }
    }
}
