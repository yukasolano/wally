package com.warren.wally.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Portfolio {

	private List<IProduto> produtos;
	private LocalDate dataRef;

	private double accrual;

	public Portfolio(List<IProduto> produtos, LocalDate dataRef) {
		super();
		this.produtos = produtos;
		this.dataRef = dataRef;
		accrual = 0;
	}

	public double getAccrual() {
		if (accrual == 0) {
			for (IProduto produto : produtos) {
				System.out.println(accrual);
				produto.calculaAccrual(dataRef);
				accrual += produto.getValorPresente();
			}
		}
		return accrual;
	}

	public Map<Object, Double> getProporcoes() {
		Map<Object, Double> proporcoes = produtos.stream()
				.collect(Collectors.groupingBy(produto -> produto.getTipoRentabilidade().toString(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));
		return proporcoes;
	}

	public Map<Object, Double> getPorInstituicoes() {
		Map<Object, Double> proporcoes = produtos.stream()
				.collect(Collectors.groupingBy(produto -> produto.getInstituicao(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));
		return proporcoes;
	}

	public Map<Object, Double> getLiquidez() {
		Map<Object, Double> proporcoes = produtos.stream()
				.collect(Collectors.groupingBy(produto -> produto.getAnoVencimento(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));
		return proporcoes;
	}

}
