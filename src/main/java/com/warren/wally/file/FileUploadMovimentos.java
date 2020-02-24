package com.warren.wally.file;

import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.repository.DividendoEntity;
import com.warren.wally.repository.DividendoRepository;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;

@Component
public class FileUploadMovimentos implements FileUpload {

	@Autowired
	private MovimentacaoRepository movimentacaoRepository;

	@Autowired
	private DividendoRepository dividendoRepository;

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

						if (tipoInvestimento.equals(TipoInvestimento.ACAO)
								|| tipoInvestimento.equals(TipoInvestimento.FII)) {

							if (tipoMovimento.equals(TipoMovimento.COMPRA)) {
								movimentacaoRepository.save(new MovimentacaoEntity(tipoInvestimento, tipoMovimento,
										data, codigo, quantidade, valorUnitario));
							} else if (tipoMovimento.equals(TipoMovimento.DIVIDENDO)) {
								dividendoRepository.save(new DividendoEntity(tipoInvestimento, tipoMovimento, data,
										codigo, quantidade, valorUnitario));
							} else {
								System.out.println("Tipo de movimento não tratado " + tipoMovimento);
								// throw new Exception("Tipo de movimento não tratado");
							}
						} else {
							System.out.println("Tipo de investimento não tratado para movimentos " + tipoMovimento);
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

}
