package com.warren.wally.model;

import com.warren.wally.repository.ProdutoEntity;

public class ProdutoFactory {

	public static Produto getProduto(ProdutoEntity entity) {

		if (entity.getTipoInvestimento() == null) {
			return null;
		}

		if (entity.getTipoInvestimento().equals(TipoInvestimento.CDB)) {
			ProdutoCDB produto = new ProdutoCDB(entity.getDtAplicacao(), entity.getVencimento(),
					entity.getValorAplicado(), entity.getTaxa());
			produto.setCorretora(entity.getCorretora());
			produto.setInstituicao(entity.getInstituicao());
			produto.setCalc(CalculadoraFactory.getCalculadora(entity.getTipoRentabilidade()));
			return produto;
		} else if (entity.getTipoInvestimento().equals(TipoInvestimento.LC)) {
			ProdutoLC produto = new ProdutoLC(entity.getDtAplicacao(), entity.getVencimento(),
					entity.getValorAplicado(), entity.getTaxa());
			produto.setCorretora(entity.getCorretora());
			produto.setInstituicao(entity.getInstituicao());
			produto.setCalc(CalculadoraFactory.getCalculadora(entity.getTipoRentabilidade()));
			return produto;
		} else if (entity.getTipoInvestimento().equals(TipoInvestimento.DEBENTURE)) {
			ProdutoDebenture produto = new ProdutoDebenture(entity.getDtAplicacao(), entity.getVencimento(),
					entity.getValorAplicado(), entity.getTaxa());
			produto.setCorretora(entity.getCorretora());
			produto.setInstituicao(entity.getInstituicao());
			produto.setCalc(CalculadoraFactory.getCalculadora(entity.getTipoRentabilidade()));
			return produto;
		} else if (entity.getTipoInvestimento().equals(TipoInvestimento.TESOURO)) {
			ProdutoTesouro produto = new ProdutoTesouro(entity.getDtAplicacao(), entity.getVencimento(),
					entity.getValorAplicado(), entity.getTaxa());
			produto.setCorretora(entity.getCorretora());
			produto.setInstituicao(entity.getInstituicao());
			produto.setCalc(CalculadoraFactory.getCalculadora(entity.getTipoRentabilidade()));
			return produto;
		}
		return null;
	}
}
