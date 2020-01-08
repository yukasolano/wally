package com.warren.wally.grafico;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraficoDados {

	public List<String> legendas;
	public List<Double> valores;
	public List<String> cores;
	public List<String> coresSecundarias;


	public void addLegenda(String legenda) {
		if (legendas == null) {
			legendas = new ArrayList<>();
		}
		legendas.add(legenda);
	}

	public void addValor(Double valor) {
		if (valores == null) {
			valores = new ArrayList<>();
		}
		valores.add(valor);
	}

}
