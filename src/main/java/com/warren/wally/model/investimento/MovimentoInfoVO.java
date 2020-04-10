package com.warren.wally.model.investimento;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MovimentoInfoVO {

    private String codigo;
    private TipoMovimento tipoMovimento;
    private LocalDate data;
    private int qunatidade;
    private double valorUnitario;
}
