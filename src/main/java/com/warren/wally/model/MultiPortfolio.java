package com.warren.wally.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.repository.ProdutoRepository;

@Component
public class MultiPortfolio {
	
	@Autowired
	private PortfolioActor portfolioActor;

	public Variacao calculaVariacoes(LocalDate dataRef, PortfolioVO portfolio) {

		Variacao variacao = new Variacao();
		
		// variacao mensal
		PortfolioVO portfolioMesAnterior = portfolioActor.run(dataRef.minusMonths(1));
		variacao.setMensalAbsoluto(portfolio.getAccrual() - portfolioMesAnterior.getAccrual());
		variacao.setMensalPorcentagem(variacao.getMensalAbsoluto() / portfolioMesAnterior.getAccrual());

		// variacao anual
		PortfolioVO portfolioAnoAnterior = portfolioActor.run(dataRef.minusYears(1));
		variacao.setAnualAbsoluto(portfolio.getAccrual() - portfolioAnoAnterior.getAccrual());
		variacao.setAnualPorcentagem(variacao.getAnualAbsoluto() / portfolioAnoAnterior.getAccrual());

		return variacao;
	}
}
