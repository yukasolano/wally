package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoRFVO implements ProdutoVO {

    private String instituicao;
    private String corretora;
    private LocalDate dtAplicacao;
    private LocalDate dtVencimento;
    private TipoInvestimento tipoInvestimento;
    private TipoRentabilidade tipoRentabilidade;
    private double valorAplicado;
    private double taxa;
    private Double valorPresente;
    private double rentabilidadeLiquida;
    private double taxaAnualLiquida;
    private double taxaMensalLiquida;
    private long du;
    private LocalDate dataReferencia;

    private String codigo;

    public String getAnoVencimento() {
        return Integer.valueOf(this.dtVencimento.getYear()).toString();
    }

    @Override
    public List<DividendoVO> getDividendos() {
        return new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %.2f %.2f %s ", tipoInvestimento, tipoRentabilidade, instituicao, taxa,
                valorAplicado, dtAplicacao);
    }

}
