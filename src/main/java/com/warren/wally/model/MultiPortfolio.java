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
	private ProdutoRepository repository;

	private List<Produto> produtos;

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void inicializa() {
		recuperaProdutos();
	}

	public void recuperaProdutos() {
		produtos = new ArrayList<>();
		repository.findAll().forEach(entity -> {
			Produto produto = ProdutoFactory.getProduto(entity);
			try {
				if (produto != null) {
					produtos.add(produto);
				}

			} catch (Exception exp) {
				System.out.println(exp);
			}
		});
	}

	public Variacao calculaVariacoes(LocalDate dataRef) {

		Variacao variacao = new Variacao();
		Portfolio portfolio = new Portfolio(produtos, dataRef);

		// variacao mensal
		LocalDate mesAnterior = dataRef.minusMonths(1);
		Portfolio portfolioMesAnterior = new Portfolio(produtos, mesAnterior);
		variacao.setMensalAbsoluto(portfolio.getAccrual() - portfolioMesAnterior.getAccrual());
		variacao.setMensalPorcentagem(variacao.getMensalAbsoluto() / portfolioMesAnterior.getAccrual());

		// variacao anual
		LocalDate anoAnterior = dataRef.minusYears(1);
		Portfolio portfolioAnoAnterior = new Portfolio(produtos, anoAnterior);
		variacao.setAnualAbsoluto(portfolio.getAccrual() - portfolioAnoAnterior.getAccrual());
		variacao.setAnualPorcentagem(variacao.getAnualAbsoluto() / portfolioAnoAnterior.getAccrual());

		return variacao;
	}
}
