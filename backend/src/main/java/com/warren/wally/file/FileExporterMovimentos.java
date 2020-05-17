package com.warren.wally.file;

import com.warren.wally.controller.ExtratoVO;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileExporterMovimentos extends AbstractFileExporter<ExtratoVO> {

    @Override
    String getFileName() {
        return "Extrato.xlsx";
    }

    @Override
    String getSheetName() {
        return "Movimentos";
    }

    @Override
    String[] getColumns() {
        return new String[]{"Tipo investimento", "Tipo movimento", "Código", "Instituição", "Tipo de rentabilidade",
                "Vencimento", "Taxa", "Data", "Quantidade", "Valor unitário", "Corretora"};
    }

    @Override
    void populateCells(List<ExtratoVO> dados,
                       Sheet sheet,
                       CellStyle dateCellStyle) {
        int rowNum = 1;

        for (ExtratoVO vo : dados) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(vo.getTipoInvestimento().toString());
            row.createCell(1).setCellValue(vo.getTipoMovimento().toString());
            row.createCell(2).setCellValue(vo.getCodigo());
            row.createCell(3).setCellValue(vo.getInstituicao());
            row.createCell(4).setCellValue((vo.getTipoRentabilidade() != null)? vo.getTipoRentabilidade().toString() : "");
            addCellDate(row, 5, vo.getVencimento(), dateCellStyle);
            row.createCell(6).setCellValue(vo.getTaxa() != null ? vo.getTaxa() : 0d);
            addCellDate(row, 7, vo.getData(), dateCellStyle);
            row.createCell(8).setCellValue(vo.getQuantidade());
            row.createCell(9).setCellValue(vo.getValor());
            row.createCell(10).setCellValue(vo.getCorretora());
        }
    }

}
