package com.warren.wally.model.cadastro;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class ProdutoRVInfoVO {

    private String codigo;
    private LocalDate data;
    private int quantidade;
    private double valorUnitario;
    private TipoInvestimento tipoInvestimento;
    private TipoRentabilidade tipoRentabilidade;
    private String instituicao;

}
