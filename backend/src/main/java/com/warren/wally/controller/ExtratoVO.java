package com.warren.wally.controller;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ExtratoVO {
    private TipoInvestimento tipoInvestimento;
    private TipoMovimento tipoMovimento;
    private String codigo;
    private TipoRentabilidade tipoRentabilidade;
    private String instituicao;
    private LocalDate vencimento;
    private Double taxa;
    private LocalDate data;
    private Integer quantidade;
    private Double valor;
    private String corretora;
}
