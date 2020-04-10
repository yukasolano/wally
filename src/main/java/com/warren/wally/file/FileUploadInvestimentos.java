package com.warren.wally.file;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class FileUploadInvestimentos implements FileUpload {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Override
    public void read(MultipartFile file) {
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();

            for (int r = 1; r < rows; r++) {
                XSSFRow row = sheet.getRow(r);
                if (row != null && !row.getCell(0).getStringCellValue().isEmpty()) {
                    TipoInvestimento tipoInvestimento = TipoInvestimento.valueOf(row.getCell(0).getStringCellValue());

                    if (tipoInvestimento.equals(TipoInvestimento.CDB) || tipoInvestimento.equals(TipoInvestimento.DEBENTURE) ||
                            tipoInvestimento.equals(TipoInvestimento.LC) || tipoInvestimento.equals(TipoInvestimento.TESOURO)) {

                        TipoRentabilidade tipoRentabilidade = TipoRentabilidade.valueOf(row.getCell(1).getStringCellValue());
                        String instituicao = row.getCell(2).getStringCellValue();

                        LocalDate vencimento = row.getCell(3).getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
                                .toLocalDate();

                        double taxa = row.getCell(4).getNumericCellValue();
                        LocalDate dtAplicacao = row.getCell(5).getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        int quantidade = (int) row.getCell(6).getNumericCellValue();
                        double valorAplicado = row.getCell(7).getNumericCellValue();
                        String corretora = row.getCell(8).getStringCellValue();

                        ProdutoEntity novoProduto = new ProdutoEntity();
                        String codigo = String.format("%s-%s-%s-%.4f-%s", instituicao, tipoInvestimento,
                                tipoRentabilidade, taxa, vencimento);
                        novoProduto.setCodigo(codigo);
                        novoProduto.setInstituicao(instituicao);
                        novoProduto.setTipoInvestimento(tipoInvestimento);
                        novoProduto.setTipoRentabilidade(tipoRentabilidade);
                        novoProduto.setVencimento(vencimento);
                        novoProduto.setTaxa(taxa);
                        produtoRepository.save(novoProduto);

                        MovimentacaoEntity movimentacao = new MovimentacaoEntity();
                        movimentacao.setTipoMovimento(TipoMovimento.COMPRA);
                        movimentacao.setCodigo(codigo);
                        movimentacao.setData(dtAplicacao);
                        movimentacao.setQuantidade(quantidade);
                        movimentacao.setValorUnitario(valorAplicado);
                        movimentacao.setCorretora(corretora);
                        movimentacaoRepository.save(movimentacao);
                    } else {
                        System.out.println("Tipo de investimento não tratado " + tipoInvestimento);
                    }

                }
            }
            wb.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
