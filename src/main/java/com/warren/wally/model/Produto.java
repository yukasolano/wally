package com.warren.wally.model;

import java.time.LocalDate;

import com.warren.wally.model.calculadora.Calculadora;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;

public abstract class Produto {
	
	protected String instituicao;
	protected String corretora;
	protected LocalDate dtAplicacao;
	protected LocalDate dtVencimento;
	protected double valorAplicado;
	protected double taxa;
	protected Calculadora calc;
	
	protected double VPLiquido = 0.0;
	protected long du = 0;
	
	public Produto(LocalDate dtAplicacao, LocalDate dtVencimento, double valorAplicado, double taxa) {
		super();
		this.dtAplicacao = dtAplicacao;
		this.dtVencimento = dtVencimento;
		this.valorAplicado = valorAplicado;
		this.taxa = taxa;
	}
	
	public void setCalc(Calculadora calc) {
		this.calc = calc;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	public String getCorretora() {
		return corretora;
	}

	public void setCorretora(String corretora) {
		this.corretora = corretora;
	}

	public double getValorAplicado() { return valorAplicado;}
	public LocalDate getVencimento() { return dtVencimento;}
	public LocalDate getDtAplicacao() { return dtAplicacao;}
	
	public String getAnoVencimento() {
		return Integer.valueOf(this.dtVencimento.getYear()).toString();
	}
	
	public double getTaxa() { return taxa;}
	
	public abstract TipoInvestimento getTipoInvestimento();
	
	public TipoRentabilidade getTipoRentabilidade() {
		return calc.getTipoRentabilidade();
	}
	
	public abstract void calculaAccrual(LocalDate hoje); 
	
	public double getValorPresente() {
		return VPLiquido;
	}

	public double getRentabilidadeLiquida() {
		return VPLiquido/valorAplicado - 1;
	}

	public double getTaxaAnualLiquida() {
		if (du == 0) { return 0;}
		try {		
			return Math.pow(getRentabilidadeLiquida() + 1, 252.0/du)-1;
		}catch(Exception exp) {
			return 0.0;
		}
	}

	public double getTaxaMensalLiquida() {
		if (du == 0) { return 0;}
		try {
			return Math.pow(getRentabilidadeLiquida() + 1, 21.0/du)-1;
		}catch(Exception exp) {
			return 0.0;
		}
	}
	
}
