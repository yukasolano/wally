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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Component
public class FileUploadMovimentos implements FileUpload {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Override
    public void read(MultipartFile file) {
        try {

            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());

            for (int iSheet = 0; iSheet < wb.getNumberOfSheets(); iSheet++) {
                XSSFSheet sheet = wb.getSheetAt(iSheet);

                for (int r = 1; r < sheet.getPhysicalNumberOfRows(); r++) {
                    XSSFRow row = sheet.getRow(r);
                    try {
                        TipoInvestimento tipoInvestimento = TipoInvestimento
                                .valueOf(row.getCell(0).getStringCellValue());
                        TipoMovimento tipoMovimento = TipoMovimento.valueOf(row.getCell(1).getStringCellValue());
                        String codigo = row.getCell(2).getStringCellValue();
                        LocalDate data = row.getCell(3).getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
                                .toLocalDate();
                        int quantidade = (int) row.getCell(4).getNumericCellValue();
                        double valorUnitario = row.getCell(5).getNumericCellValue();

                        if (!tipoInvestimento.equals(TipoInvestimento.ACAO)
                                && !tipoInvestimento.equals(TipoInvestimento.FII)) {
                            System.out.println("Tipo de investimento não tratado para movimentos " + tipoInvestimento);
                            continue;
                        }

                        if (!tipoMovimento.equals(TipoMovimento.COMPRA) && !tipoMovimento.equals(TipoMovimento.DIVIDENDO) &&
                                !tipoMovimento.equals(TipoMovimento.JCP) && !tipoMovimento.equals(TipoMovimento.ATUALIZACAO)) {
                            System.out.println("Tipo de movimento não tratado " + tipoMovimento);
                            continue;
                        }

                        List<ProdutoEntity> produto = produtoRepository.findByCodigo(codigo);
                        if (produto.isEmpty()) {
                            System.out.println("Produto não cadastrado " + codigo);
                            if (tipoInvestimento.equals(TipoInvestimento.ACAO)
                                    || tipoInvestimento.equals(TipoInvestimento.FII)) {
                                //vou cadastrar pois tenho tadas as infos
                                ProdutoEntity novoProduto = new ProdutoEntity();
                                novoProduto.setCodigo(codigo);
                                novoProduto.setTipoInvestimento(tipoInvestimento);
                                novoProduto.setTipoRentabilidade(getTipoRentabilidade(tipoInvestimento));
                                novoProduto.setInstituicao("Renda variável");
                                produtoRepository.save(novoProduto);

                                MovimentacaoEntity entity = new MovimentacaoEntity();
                                entity.setTipoMovimento(tipoMovimento);
                                entity.setValorUnitario(valorUnitario);
                                entity.setQuantidade(quantidade);
                                entity.setData(data);
                                entity.setCodigo(codigo);
                                movimentacaoRepository.save(entity);
                            }
                        } else {
                            MovimentacaoEntity entity = new MovimentacaoEntity();
                            entity.setTipoMovimento(tipoMovimento);
                            entity.setValorUnitario(valorUnitario);
                            entity.setQuantidade(quantidade);
                            entity.setData(data);
                            entity.setCodigo(codigo);
                            movimentacaoRepository.save(entity);
                        }


                    } catch (Exception e) {
                        System.out.println("Erro na linha" + r + ": " + e.getMessage());
                    }
                }
            }
            wb.close();
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }

    }

    private TipoRentabilidade getTipoRentabilidade(TipoInvestimento tipoInvestimento) {
        for (TipoRentabilidade tipoRentabilidade : TipoRentabilidade.values()) {
            if (tipoRentabilidade.toString().equals(tipoInvestimento.toString())) {
                return tipoRentabilidade;
            }
        }
        return null;
    }

}
