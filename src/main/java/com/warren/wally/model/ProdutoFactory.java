package com.warren.wally.model;

import com.warren.wally.repository.ProdutoEntity;

public class ProdutoFactory {
	
	public static IProduto getProduto(ProdutoEntity entity) {
		if(entity.getTipoInvestimento().equals(TipoInvestimento.CBD)) {
			ProdutoCDB produto = new ProdutoCDB(entity.getDtAplicacao(), 
					entity.getVencimento(), entity.getValorAplicado(), entity.getTaxa());
			produto.setCorretora(entity.getCorretora());
			produto.setInstituicao(entity.getInstituicao());
			produto.setCalc(CalculadoraFactory.getCalculadora(entity.getTipoRentabilidade()));
			return produto;
		}
		return null;
	}
}
