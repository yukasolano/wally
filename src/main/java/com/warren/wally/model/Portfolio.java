package com.warren.wally.model;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Portfolio {

	private List<Produto> produtos;
	private LocalDate dataRef;

	private double accrual;

	public Portfolio(List<Produto> produtos, LocalDate dataRef) {
		super();
		this.produtos = produtos;
		this.dataRef = dataRef;
		accrual = 0;
	}

	public double getAccrual() {
		if (accrual == 0) {
			for (Produto produto : produtos) {
				System.out.println(accrual);
				produto.calculaAccrual(dataRef);
				accrual += produto.getValorPresente();
			}
		}
		return accrual;
	}

	public Map<String, Double> getProporcoes() {
		Map<String, Double> proporcoes = produtos.stream()
				.collect(Collectors.groupingBy(produto -> produto.getTipoRentabilidade().toString(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));
		return sortedByKey(proporcoes);
	}

	public Map<String, Double> getPorInstituicoes() {
		Map<String, Double> proporcoes = produtos.stream()
				.collect(Collectors.groupingBy(produto -> produto.getInstituicao(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));
		return sortedByKey(proporcoes);
	}

	public Map<String, Double> getLiquidez() {
		Map<String, Double> proporcoes = produtos.stream()
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
