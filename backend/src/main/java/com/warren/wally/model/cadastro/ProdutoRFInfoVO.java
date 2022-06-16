package com.warren.wally.model.cadastro;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProdutoRFInfoVO {

    private String corretora;
    private String instituicao;
    private TipoInvestimento tipoInvestimento;
    private TipoRentabilidade tipoRentabilidade;
    private LocalDate dtAplicacao;
    private LocalDate dtVencimento;
    private double taxa;
    private double valorAplicado;

    @Override
    public String toString() {
        return String.format("%s %s %s %s %s %s %s %s", corretora, instituicao, tipoInvestimento,
                tipoRentabilidade, dtAplicacao, dtVencimento, taxa, valorAplicado);
    }
}
