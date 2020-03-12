package com.warren.wally.file;

import com.warren.wally.repository.MovimentacaoEntity;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

@Component
public class FileExporterMovimentos extends AbstractFileExporter<MovimentacaoEntity> {

    @Override
    String getFileName() {
        return "Movimentacoes.xlsx";
    }

    @Override
    String getSheetName() {
        return "Movimentos";
    }

    @Override
    String[] getColumns() {
        return new String[]{"Tipo investimento", "Tipo movimento", "Data",
                "Código", "Quantidade", "Valor unitário"};
    }

    @Override
    void populateCells(List<MovimentacaoEntity> dados,
                       Sheet sheet,
                       CellStyle dateCellStyle) {
        int rowNum = 1;

        for (MovimentacaoEntity produto : dados) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(produto.getTipoInvestimento().toString());
            row.createCell(1).setCellValue(produto.getTipoMovimento().toString());
            Cell data = row.createCell(2);
            data.setCellValue(convertToDate(produto.getData()));
            data.setCellStyle(dateCellStyle);
            row.createCell(3).setCellValue(produto.getCodigo());
            row.createCell(4).setCellValue(produto.getQuantidade());
            row.createCell(5).setCellValue(produto.getValorUnitario());
        }
    }
}
