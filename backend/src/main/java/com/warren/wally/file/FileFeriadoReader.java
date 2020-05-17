package com.warren.wally.file;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Component
public class FileFeriadoReader {

    public List<LocalDate> read() {
        List<LocalDate> feriados = new ArrayList<>();
        try {
            Workbook wb = WorkbookFactory.create(new File("feriados_nacionais.xls"));
            Sheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();

            for (int r = 1; r < rows; r++) {
                Row row = sheet.getRow(r);
                if (row != null && row.getCell(0).getCellType().equals(CellType.NUMERIC)) {
                    feriados.add(row.getCell(0).getDateCellValue().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate());
                }
            }
            wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return feriados;
    }
}