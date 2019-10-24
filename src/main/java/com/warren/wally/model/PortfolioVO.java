package com.warren.wally.model;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.warren.wally.model.calculadora.TipoRentabilidade;

public class PortfolioVO {

	private List<Produto> produtosRF;

	private List<ProdutoFIIVO> produtosRV;

	private double accrual;

	public void setProdutos(List<Produto> produtosRF) {
		this.produtosRF = produtosRF;
	}

	public void setProdutosRV(List<ProdutoFIIVO> produtosRV) {
		this.produtosRV = produtosRV;
	}

	public List<Produto> getProdutosRF() {
		return produtosRF;
	}
	
	public void setProdutosRF(List<Produto> produtosRF) {
		this.produtosRF = produtosRF;
	}
	
	public void setAccrual(double accrual) {
		this.accrual = accrual;
	}

	public double getAccrual() {
		return accrual;
	}

	public Map<String, Double> getProporcoes() {
		Map<String, Double> proporcoes = produtosRF.stream()
				.collect(Collectors.groupingBy(produto -> produto.getTipoRentabilidade().toString(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));

		Double somaFII = new Double(produtosRV.stream().mapToDouble(produto -> produto.getPrecoTotal()).sum());
		proporcoes.put(TipoRentabilidade.FII.toString(), somaFII);
		return sortedByKey(proporcoes);
	}

	public Map<String, Double> getProporcoesRV() {
		Map<String, Double> proporcoes = produtosRV.stream()
				.collect(Collectors.groupingBy(produto -> produto.getCodigo(),
						Collectors.reducing(0.0, produto -> produto.getPrecoTotal(), Double::sum)));
		return sortedByKey(proporcoes);
	}


	public List<ProdutoFIIVO> getProdutosRV() {
		return produtosRV;
	}

	public Map<String, Double> getPorInstituicoes() {
		Map<String, Double> proporcoes = produtosRF.stream()
				.collect(Collectors.groupingBy(produto -> produto.getInstituicao(),
						Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));
		return sortedByKey(proporcoes);
	}

	public Map<String, Double> getLiquidez() {
		Map<String, Double> proporcoes = produtosRF.stream()
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
