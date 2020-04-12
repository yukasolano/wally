package com.warren.wally.file;

import com.warren.wally.model.cadastro.CadastroProdutoResolver;
import com.warren.wally.model.cadastro.ProdutoInfoVO;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.repository.MovimentacaoRepository;
import com.warren.wally.repository.ProdutoRepository;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class FileUploadMovimentos implements FileUpload {

    @Autowired
    private MovimentacaoRepository movimentacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Resource
    private CadastroProdutoResolver cadastroProdutoResolver;

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

                        TipoRentabilidade tipoRentabilidade = TipoRentabilidade.valueOf(row.getCell(3).getStringCellValue());
                        String instituicao = getString(row.getCell(4));
                        LocalDate vencimento = getDate(row.getCell(5));
                        double taxa = getNumber(row.getCell(6));

                        LocalDate data = getDate(row.getCell(7));
                        int quantidade = (int) getNumber(row.getCell(8));
                        double valorUnitario = getNumber(row.getCell(9));
                        String corretora = getString(row.getCell(10));

                        ProdutoInfoVO vo = new ProdutoInfoVO();
                        vo.setTipoInvestimento(tipoInvestimento);
                        vo.setTipoMovimento(tipoMovimento);
                        vo.setCodigo(codigo);
                        vo.setInstituicao(instituicao);
                        vo.setTipoRentabilidade(tipoRentabilidade);
                        vo.setTaxa(taxa);
                        vo.setDtVencimento(vencimento);
                        vo.setData(data);
                        vo.setQuantidade(quantidade);
                        vo.setValorUnitario(valorUnitario);
                        vo.setCorretora(corretora);

                        cadastroProdutoResolver.resolve(tipoInvestimento, tipoMovimento).saveGeneric(vo);

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


    private LocalDate getDate(XSSFCell cell) {
        if (cell != null && cell.getDateCellValue() != null) {
            return cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
                    .toLocalDate();
        }
        return null;
    }

    private String getString(XSSFCell cell) {
        return cell != null ? cell.getStringCellValue() : null;
    }

    private double getNumber(XSSFCell cell) {
        return cell != null ? cell.getNumericCellValue() : 0d;
    }

}
