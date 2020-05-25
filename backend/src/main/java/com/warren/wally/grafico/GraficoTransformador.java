package com.warren.wally.grafico;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GraficoTransformador {

	public GraficoDados transforma(Map<String, Double> dados) {
		GraficoDados graficoDados = new GraficoDados();

		dados.forEach((key, value) -> {
			graficoDados.addLegenda((String) key);
			graficoDados.addValor(Math.floor(value * 100) / 100.0);
		});

		return graficoDados;

	}
}
