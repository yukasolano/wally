package com.warren.wally.file;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.warren.wally.repository.MovimentacaoEntity;

@Component
public class FileExporterResolver {

	@Resource
	private FileExporterInvestimentos exportInvestimentos;

	@Resource
	private FileExporterMovimentos exportMovimentos;
	
	@Resource
	private FileExporterGenerico exportGenerico;

	public FileExporter resolve(TypeFile typeFile, List<MovimentacaoEntity> movimentacao) {
		if (typeFile.equals(TypeFile.INVESTIMENTOS)) {
			return exportInvestimentos;
		}

		if (typeFile.equals(TypeFile.MOVIMENTOS)) {
			return exportMovimentos;
		}
		
		if (typeFile.equals(TypeFile.GENERICO)) {
			exportGenerico.setData(movimentacao);
			return exportGenerico;
		}
		throw new RuntimeException("Tipo de arquivo " + typeFile + " não tratado.");
	}

}
