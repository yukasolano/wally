package com.warren.wally.file;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;

@Component
public class FileExporterInvestimentos extends AbstractFileExporter {

	@Autowired
	private ProdutoRepository produtoRepository;

	private static String[] columns = { "Instituição", "Ano", "Vencimento", "Tipo de investimento",
			"Tipo de rentabilidade", "Taxa", "Corretora", "Data aplicação", "Valor aplicado" };

	public ExportedFile export() {
		Workbook workbook = new XSSFWorkbook();

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Sheet
		Sheet sheet = workbook.createSheet("Investimentos");

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create a Row
		Row headerRow = sheet.createRow(0);

		// Create cells
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create Other rows and cells with employees data
		int rowNum = 1;

		Iterable<ProdutoEntity> produtos = produtoRepository.findAll();
		for (ProdutoEntity produto : produtos) {
			Row row = sheet.createRow(rowNum++);

			row.createCell(0).setCellValue(produto.getInstituicao());

			Cell vencimento = row.createCell(2);
			vencimento.setCellValue(convertToDate(produto.getVencimento()));
			vencimento.setCellStyle(dateCellStyle);

			row.createCell(3).setCellValue(produto.getTipoInvestimento().toString());
			row.createCell(4).setCellValue(produto.getTipoRentabilidade().toString());
			row.createCell(5).setCellValue(produto.getTaxa());
			row.createCell(6).setCellValue(produto.getCorretora());

			Cell aplicacao = row.createCell(7);
			aplicacao.setCellValue(convertToDate(produto.getDtAplicacao()));
			aplicacao.setCellStyle(dateCellStyle);

			row.createCell(8).setCellValue(produto.getValorAplicado());

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}

		return new ExportedFile("Investimentos.xlsx", writeFile(workbook));

	}
}