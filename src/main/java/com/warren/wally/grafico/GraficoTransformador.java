package com.warren.wally.grafico;

import java.util.Map;

public class GraficoTransformador {

	private Cores cores = new Cores();

	public GraficoDados transforma(Map<String, Double> dados) {
		return transforma(dados, false);
	}

	public GraficoDados transforma(Map<String, Double> dados, boolean coresClaras) {
		GraficoDados graficoDados = new GraficoDados();

		dados.forEach((key, value) -> {
			graficoDados.addLegenda((String) key);
			graficoDados.addValor(Math.floor(value * 100) / 100.0);
		});

		if (coresClaras) {
			graficoDados.setCores(cores.getCoresBorda(dados.size()));
			graficoDados.setCoresSecundarias(cores.getCoresClaras(dados.size()));
		} else {
			graficoDados.setCores(cores.getCores(dados.size()));
			graficoDados.setCoresSecundarias(cores.getCoresHover(dados.size()));
		}

		return graficoDados;

	}
}
