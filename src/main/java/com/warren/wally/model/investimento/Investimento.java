package com.warren.wally.model.investimento;

import java.time.LocalDate;

import com.warren.wally.model.ProdutoVO;
import com.warren.wally.repository.ProdutoEntity;

public interface Investimento {

	ProdutoVO calc(LocalDate hoje, ProdutoEntity produto);
	TipoInvestimento getTipoInvestimento(); 

}
