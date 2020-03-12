package com.warren.wally.file;

import com.warren.wally.model.investimento.ProdutoRFVO;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

@Component
public class FileExporterProdRF extends AbstractFileExporter<ProdutoRFVO> {

    @Override
    String getFileName() {
        return "Produtos-RF.xlsx";
    }

    @Override
    String getSheetName() {
        return "Produtos Renda Fixa";
    }

    @Override
    String[] getColumns() {
        return new String[]{"Instituição", "Tipo de investimento", "Tipo de rentabilidade", "Data aplicação",
                "Data de vencimento", "Valor aplicado", "Accrual", "Rentabilidade líquidea", "Taxa a.m.", "Taxa a.a."};
    }

    @Override
    void populateCells(List<ProdutoRFVO> dados,
                       Sheet sheet,
                       CellStyle dateCellStyle) {
        int rowNum = 1;

        for (ProdutoRFVO produto : dados) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(produto.getInstituicao());
            row.createCell(1).setCellValue(produto.getTipoInvestimento().toString());
            row.createCell(2).setCellValue(produto.getTipoRentabilidade().toString());

            Cell aplicacao = row.createCell(3);
            aplicacao.setCellValue(convertToDate(produto.getDtAplicacao()));
            aplicacao.setCellStyle(dateCellStyle);

            Cell vencimento = row.createCell(4);
            vencimento.setCellValue(convertToDate(produto.getDtVencimento()));
            vencimento.setCellStyle(dateCellStyle);

            row.createCell(5).setCellValue(produto.getValorAplicado());
            row.createCell(6).setCellValue(produto.getValorPresente());
            row.createCell(7).setCellValue(produto.getRentabilidadeLiquida());
            row.createCell(8).setCellValue(produto.getTaxaMensalLiquida());
            row.createCell(9).setCellValue(produto.getTaxaAnualLiquida());

        }
    }
}