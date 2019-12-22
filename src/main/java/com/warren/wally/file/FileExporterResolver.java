package com.warren.wally.file;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class FileExporterResolver {

	@Resource
	private FileExporterInvestimentos exportInvestimentos;

	@Resource
	private FileExporterMovimentos exportMovimentos;

	public FileExporter resolve(TypeFile typeFile) {
		if (typeFile.equals(TypeFile.INVESTIMENTOS)) {
			return exportInvestimentos;
		}

		if (typeFile.equals(TypeFile.MOVIMENTOS)) {
			return exportMovimentos;
		}
		throw new RuntimeException("Tipo de arquivo " + typeFile + " não tratado.");
	}

}
