package com.warren.wally.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.model.calculadora.Calculadora;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.investimento.Investimento;
import com.warren.wally.model.investimento.InvestimentoResolver;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;

@Component
public class PortfolioActor {

	@Resource
	private ProdutoFIIActor actorFII;

	@Autowired
	private ProdutoRepository repository;

	private List<Produto> produtos;
	
	private List<ProdutoEntity> produtosEnt;
	
	@Resource
	private CalculadoraResolver calculadoraResolver;
	
	@Resource
	private InvestimentoResolver investimentoResolver;

	public List<ProdutoEntity> getProdutos() {
		if (produtosEnt == null) {
			produtosEnt = recuperaProdutos();
		}
		return produtosEnt;
	}

	public List<ProdutoFIIVO> getProdutosRV(LocalDate dataRef) {
		return actorFII.run(dataRef);
	}

	public List<ProdutoEntity> recuperaProdutos() {
		List<ProdutoEntity> prods = new ArrayList<>();
		/*
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
		return produtos;*/
		repository.findAll().forEach(prods::add);
		return prods;
	}

	public PortfolioVO run(LocalDate dataRef) {
		List<ProdutoEntity> produtosRF = getProdutos();
		List<ProdutoFIIVO> produtosRV = getProdutosRV(dataRef);

	List<ProdutoVO> produtos = produtosRF.stream().map(p -> {
		Calculadora calc = calculadoraResolver.resolve(p.getTipoRentabilidade());
		Investimento invest =  investimentoResolver.resolve(p.getTipoInvestimento());
		
		ProdutoVO prod = invest.calc(dataRef, p);
		return prod;
		
	}
		).collect(Collectors.toList());
		
	/*
		double accrual = 0.0;
		for (Produto produto : getProdutos()) {
			System.out.println(accrual);
			produto.calculaAccrual(dataRef);
			accrual += produto.getValorPresente();
		}
		for (ProdutoFIIVO produto : produtosRV) {
			accrual += produto.getValorPresente();
		}
*/
		PortfolioVO portfolio = new PortfolioVO();
		//portfolio.setProdutos(produtosRF);
		portfolio.setProdutosRV(produtosRV);
		//portfolio.setAccrual(accrual);

		return portfolio;

	}
}
