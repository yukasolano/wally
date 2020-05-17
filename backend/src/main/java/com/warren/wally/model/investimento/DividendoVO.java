package com.warren.wally.model.investimento;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DividendoVO {

    private String codigo;
    private LocalDate data;
    private int quantidade;
    private double valorUnitario;
    private TipoMovimento tipo;
}
