package com.warren.wally.model.cadastro;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ProdutoInfoVO {

    private TipoInvestimento tipoInvestimento;
    private TipoMovimento tipoMovimento;

    private String codigo;

    private String instituicao;
    private TipoRentabilidade tipoRentabilidade;
    private double taxa;
    private LocalDate dtVencimento;

    private LocalDate data;
    private int quantidade;
    private double valorUnitario;

    private String corretora;
}
