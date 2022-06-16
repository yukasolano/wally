package com.warren.wally.file;

import com.warren.wally.model.investimento.ProdutoRVVO;
import java.util.List;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Component;

@Component
public class FileExporterProdRV extends AbstractFileExporter<ProdutoRVVO> {

    @Override
    String getFileName() {
        return "Produtos-RV.xlsx";
    }

    @Override
    String getSheetName() {
        return "Produtos Renda Variável";
    }

    @Override
    String[] getColumns() {
        return new String[]{"Código", "Quantidade", "Preço médio", "Cotação",
                "Valor presente", "Resultado", "Rentabilidade"};
    }

    @Override
    void populateCells(List<ProdutoRVVO> dados,
                       Sheet sheet,
                       CellStyle dateCellStyle) {
        int rowNum = 1;
        for (ProdutoRVVO produto : dados) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(produto.getCodigo());
            row.createCell(1).setCellValue(produto.getQuantidade());
            row.createCell(2).setCellValue(produto.getPrecoMedio());
            row.createCell(3).setCellValue(produto.getCotacao());
            row.createCell(4).setCellValue(produto.getValorPresente());
            row.createCell(5).setCellValue(produto.getResultado());
            row.createCell(6).setCellValue(produto.getRentabilidadeDividendo());
        }
    }

}