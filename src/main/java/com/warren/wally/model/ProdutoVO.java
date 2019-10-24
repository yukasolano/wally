package com.warren.wally.model;

import java.time.LocalDate;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProdutoVO {

	private String instituicao;
	private String corretora;
	private LocalDate dtAplicacao;
	private LocalDate dtVencimento;
	private TipoInvestimento tipoInvestimento;
	private TipoRentabilidade tipoRentabilidade;
	private double valorAplicado;
	private double taxa;
	private double valorPresente;
	private double rentabilidadeLiquida;
	private double taxaAnualLiquida;
	private double taxaMensalLiquida;
	private long du;
	private LocalDate dataReferencia;
	
}
