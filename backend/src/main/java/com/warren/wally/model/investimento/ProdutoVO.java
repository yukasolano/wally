package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.TipoRentabilidade;

import java.time.LocalDate;
import java.util.List;

public interface ProdutoVO {

    String getCodigo();

    String getInstituicao();

    Double getValorPresente();

    TipoRentabilidade getTipoRentabilidade();

    String getAnoVencimento();

    List<DividendoVO> getDividendos();

    Double getValorAplicado();

    Double getRentabilidade(ProdutoVO vo);

    LocalDate getDataReferencia();
}
