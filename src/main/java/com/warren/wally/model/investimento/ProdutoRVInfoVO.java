package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.TipoRentabilidade;
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

}
