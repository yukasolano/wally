package com.warren.wally.model.investimento;

import java.time.LocalDate;

import com.warren.wally.model.investimento.repository.ProdutoEntity;

public interface Investimento {

	TipoInvestimento getTipoInvestimento();

	ProdutoVO calc(LocalDate hoje, ProdutoEntity produto);

	ProdutoVO calcAcum(LocalDate hoje, ProdutoVO produto);

}
