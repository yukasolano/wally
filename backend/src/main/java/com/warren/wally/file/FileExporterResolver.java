package com.warren.wally.file;

import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class FileExporterResolver {

    @Resource
    private FileExporterProdRF exportProdRF;

    @Resource
    private FileExporterMovimentos exportMovimentos;

    @Resource
    private FileExporterProdRV exportProdRV;


    public FileExporter resolve(TypeFile typeFile) {
        if (typeFile.equals(TypeFile.PRODUTOS_RF)) {
            return exportProdRF;
        }

        if (typeFile.equals(TypeFile.PRODUTOS_RV)) {
            return exportProdRV;
        }

        if (typeFile.equals(TypeFile.MOVIMENTOS)) {
            return exportMovimentos;
        }

        throw new RuntimeException("Tipo de arquivo " + typeFile + " não tratado.");
    }

}
