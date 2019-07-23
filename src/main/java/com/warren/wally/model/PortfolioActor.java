package com.warren.wally.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.repository.ProdutoRepository;

@Component
public class PortfolioActor {

	@Resource
	private ProdutoFIIActor actorFII;

	@Autowired
	private ProdutoRepository repository;

	private List<Produto> produtos;

	public List<Produto> getProdutos() {
		if (produtos == null) {
			produtos = recuperaProdutos();
		}
		return produtos;
	}

	public List<ProdutoFIIVO> getProdutosRV(LocalDate dataRef) {
		return actorFII.run(dataRef);
	}

	public List<Produto> recuperaProdutos() {
		List<Produto> produtos = new ArrayList<>();
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
		return produtos;
	}

	public PortfolioVO run(LocalDate dataRef) {
		List<Produto> produtosRF = getProdutos();
		List<ProdutoFIIVO> produtosRV = getProdutosRV(dataRef);

		double accrual = 0.0;
		for (Produto produto : getProdutos()) {
			System.out.println(accrual);
			produto.calculaAccrual(dataRef);
			accrual += produto.getValorPresente();
		}
		for (ProdutoFIIVO produto : produtosRV) {
			accrual += produto.getValorPresente();
		}

		PortfolioVO portfolio = new PortfolioVO();
		portfolio.setProdutos(produtosRF);
		portfolio.setProdutosRV(produtosRV);
		portfolio.setAccrual(accrual);

		return portfolio;

	}
}
