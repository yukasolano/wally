package com.warren.wally.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.repository.ProdutoRepository;

@Component
public class Portfolio {

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

	public double getAccrual(LocalDate dataRef) {
		double accrual = 0.0;
		for (Produto produto : getProdutos()) {
			System.out.println(accrual);
			produto.calculaAccrual(dataRef);
			accrual += produto.getValorPresente();
		}
		return accrual;
	}

	public Map<String, Double> getProporcoes(LocalDate dataRef) {
		Map<String, Double> proporcoes = getProdutos().stream()
				.collect(Collectors.groupingBy(produto -> produto.getTipoRentabilidade().toString(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));
		List<ProdutoFIIVO> produtos = actorFII.run(dataRef);
		Double somaFII = new Double(produtos.stream().mapToDouble(produto -> produto.getPrecoTotal()).sum());
		proporcoes.put(TipoRentabilidade.FII.toString(), somaFII);
		return sortedByKey(proporcoes);
	}

	public Map<String, Double> getProporcoesRV(LocalDate dataRef) {
		List<ProdutoFIIVO> produtos = actorFII.run(dataRef);
		Map<String, Double> proporcoes = produtos.stream().collect(Collectors.groupingBy(produto -> produto.getCodigo(),
				Collectors.reducing(0.0, produto -> produto.getPrecoTotal(), Double::sum)));
		return sortedByKey(proporcoes);
	}

	public Map<String, Double> getPorInstituicoes() {
		Map<String, Double> proporcoes = getProdutos().stream()
				.collect(Collectors.groupingBy(produto -> produto.getInstituicao(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));
		return sortedByKey(proporcoes);
	}

	public Map<String, Double> getLiquidez() {
		Map<String, Double> proporcoes = getProdutos().stream()
				.collect(Collectors.groupingBy(produto -> produto.getAnoVencimento(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));

		return sortedByKey(proporcoes);
	}

	private Map<String, Double> sortedByKey(final Map<String, Double> inputMap) {
		final Map<String, Double> sorted = inputMap.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return sorted;
	}
}
