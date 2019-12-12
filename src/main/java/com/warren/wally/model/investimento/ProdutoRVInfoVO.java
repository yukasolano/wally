package com.warren.wally.model.investimento;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProdutoRVInfoVO {

	private String codigo;
	private LocalDate data;
	private int quantidade;
	private double valorUnitario;
	private String tipo;
	
}
