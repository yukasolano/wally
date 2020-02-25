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

import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;

@Component
public class FileExporterMovimentos extends AbstractFileExporter {

	@Autowired
	private MovimentacaoRepository movimentacaoRepository;

	private static String[] columnsCompra = { "Código", "Data", "Quantidade", "Valor unitário" };

	public ExportedFile export() {
		Workbook workbook = new XSSFWorkbook();

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		// Create Cell Style for formatting Date
		CellStyle dateCellStyle = workbook.createCellStyle();
		dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

		// Create a Sheet
		Sheet sheetCompra = workbook.createSheet("Compra");
		// Create a Row
		Row headerRow = sheetCompra.createRow(0);

		// Create cells
		for (int i = 0; i < columnsCompra.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columnsCompra[i]);
			cell.setCellStyle(headerCellStyle);
		}

		// Create Other rows and cells with employees data
		int rowNum = 1;

		Iterable<MovimentacaoEntity> produtos = movimentacaoRepository.findAll();
		for (MovimentacaoEntity produto : produtos) {
			Row row = sheetCompra.createRow(rowNum++);

			row.createCell(0).setCellValue(produto.getCodigo());

			Cell data = row.createCell(1);
			data.setCellValue(convertToDate(produto.getData()));
			data.setCellStyle(dateCellStyle);

			row.createCell(2).setCellValue(produto.getQuantidade());
			row.createCell(3).setCellValue(produto.getValorUnitario());

		}

		// Resize all columns to fit the content size
		for (int i = 0; i < columnsCompra.length; i++) {
			sheetCompra.autoSizeColumn(i);
		}

		return new ExportedFile("Movimentacoes.xlsx", writeFile(workbook));

	}

	private CellStyle getHeaderStyle(Workbook workbook) {
		// Create a Font for styling header cells
		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);

		// Create a CellStyle with the font
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFont(headerFont);

		return headerCellStyle;
	}

}
