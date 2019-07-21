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
	private Portfolio portfolio;

	public Variacao calculaVariacoes(LocalDate dataRef) {

		Variacao variacao = new Variacao();
		//Portfolio portfolio = new Portfolio(getProdutos(), dataRef);

		// variacao mensal
		LocalDate mesAnterior = dataRef.minusMonths(1);
		//Portfolio portfolioMesAnterior = new Portfolio(getProdutos(), mesAnterior);
		variacao.setMensalAbsoluto(portfolio.getAccrual(dataRef) - portfolio.getAccrual(mesAnterior));
		variacao.setMensalPorcentagem(variacao.getMensalAbsoluto() / portfolio.getAccrual(mesAnterior));

		// variacao anual
		LocalDate anoAnterior = dataRef.minusYears(1);
		//Portfolio portfolioAnoAnterior = new Portfolio(getProdutos(), anoAnterior);
		variacao.setAnualAbsoluto(portfolio.getAccrual(dataRef) - portfolio.getAccrual(anoAnterior));
		variacao.setAnualPorcentagem(variacao.getAnualAbsoluto() / portfolio.getAccrual(anoAnterior));

		return variacao;
	}
}
