package com.warren.wally.file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.core.io.ByteArrayResource;

public abstract class AbstractFileExporter implements FileExporter {

	protected ByteArrayResource writeFile(Workbook workbook) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			ByteArrayResource resource = new ByteArrayResource(bos.toByteArray());
			bos.close();
			workbook.close();
			return resource;
		} catch (IOException e) {
			return null;
		}
	}
	
	protected Date convertToDate(LocalDate dateToConvert) {
		return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}
}
