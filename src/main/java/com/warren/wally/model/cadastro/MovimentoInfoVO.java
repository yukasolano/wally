package com.warren.wally.model.cadastro;

import com.warren.wally.model.investimento.TipoMovimento;
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
