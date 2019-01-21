package com.warren.wally.model;

import java.time.LocalDate;

public interface IProduto {

	void calculaAccrual(LocalDate hoje);
	
	double getValorPresente();
	
	double getRentabilidadeLiquida();
	
	double getTaxaAnualLiquida();
	
	double getTaxaMensalLiquida();
	
	TipoInvestimento getTipoInvestimento();
	
	TipoRentabilidade getTipoRentabilidade();
	
	public String getInstituicao();

	public String getCorretora();

}
