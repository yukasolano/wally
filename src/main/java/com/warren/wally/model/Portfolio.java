package com.warren.wally.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warren.wally.repository.ProdutoRepository;

@Component
public class Portfolio {

	@Autowired
	private ProdutoRepository repository;

	private List<IProduto> produtos;
	private LocalDate dataRef;

	private List<IProduto> getProdutos() {
		if (produtos == null) {
			produtos = new ArrayList<>();
			repository.findAll().forEach(entity -> {
				IProduto produto = ProdutoFactory.getProduto(entity);
				try {
					if (produto != null) {
						produtos.add(produto);
					}

				} catch (Exception exp) {
					System.out.println(exp);
				}
			});
		}
		return produtos;
	}

	public double getAccrual(LocalDate dataRef) {
		produtos = getProdutos();
		double accrual = 0;
		for (IProduto produto : produtos) {
			System.out.println(accrual);
			produto.calculaAccrual(dataRef);
			accrual += produto.getValorPresente();
		}
		return accrual;
	}

	public String getProporcoes()  {
		Map<Object, Double> proporcoes = produtos.stream().collect(Collectors.groupingBy(produto -> produto.getTipoRentabilidade().toString(),
				Collectors.reducing(0.0, produto -> produto.getValorPresente(), Double::sum)));
		System.out.println(proporcoes);
		
		try {
			String json = new ObjectMapper().writeValueAsString(proporcoes);
			System.out.println(json);
			return json.replaceAll("\"", "");
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}

}
