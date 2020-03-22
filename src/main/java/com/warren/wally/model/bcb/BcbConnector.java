package com.warren.wally.model.bcb;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

import java.util.List;

public interface BcbConnector {

    @RequestLine("GET bcdata.sgs.{codigo}/dados?formato={formato}&dataInicial={dataInicial}&dataFinal={dataFinal}")
    @Headers({"Content-Type: application/json", "Prefer: odata.maxpagesize=10"})
    List<BcbResponse> cdiSearch(
            @Param("codigo") String codigo,
            @Param("formato") String formato,
            @Param("dataInicial") String dataInicial,
            @Param("dataFinal") String dataFinal);

}
