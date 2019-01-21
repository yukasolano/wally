package com.warren.wally.model;

import java.time.LocalDate;

public class ProdutoCDB implements IProduto{
	
	public ProdutoCDB(LocalDate dtAplicacao, LocalDate dtVencimento, double valorAplicado, double taxa) {
		super();
		this.dtAplicacao = dtAplicacao;
		this.dtVencimento = dtVencimento;
		this.valorAplicado = valorAplicado;
		this.taxa = taxa;
	}
	
	private Calculadora calc;
	
	public void setCalc(Calculadora calc) {
		this.calc = calc;
	}

	private String instituicao;
	private String corretora;

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

	private LocalDate dtAplicacao;
	private LocalDate dtVencimento;
	private double valorAplicado;
	private double taxa;
	
	public double getValorAplicado() { return valorAplicado;}
	public LocalDate getVencimento() { return dtVencimento;}
	public LocalDate getDtAplicacao() { return dtAplicacao;}
	
	public double getTaxa() { return taxa;}
	private double VPLiquido = 0.0;
	private long du = 0;
	
	public TipoInvestimento getTipoInvestimento() {
		return TipoInvestimento.CBD;
	}
	
	public TipoRentabilidade getTipoRentabilidade() {
		return calc.getTipoRentabilidade();
	}
	
	
	@Override
	public void calculaAccrual(LocalDate hoje) {
		du = new BussinessDaysCalendar().getDu(dtAplicacao, hoje);
		double VPBruto = calc.calculaVPBruto(valorAplicado, taxa, dtAplicacao, hoje);
		double ir = new Leao().getIR(VPBruto-valorAplicado, dtAplicacao, hoje);
		VPLiquido = VPBruto - ir;
	}
	
	@Override
	public double getValorPresente() {
		return VPLiquido;
	}
	@Override
	public double getRentabilidadeLiquida() {
		return VPLiquido/valorAplicado - 1;
	}
	@Override
	public double getTaxaAnualLiquida() {
		return Math.pow(getRentabilidadeLiquida() + 1, 252.0/du)-1;
	}
	@Override
	public double getTaxaMensalLiquida() {
		return Math.pow(getRentabilidadeLiquida() + 1, 21.0/du)-1;
	}
	

}
