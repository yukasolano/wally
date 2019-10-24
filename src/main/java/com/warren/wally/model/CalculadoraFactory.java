package com.warren.wally.model;

import com.warren.wally.model.calculadora.Calculadora;
import com.warren.wally.model.calculadora.TipoRentabilidade;

public class CalculadoraFactory {
	public static Calculadora getCalculadora(TipoRentabilidade tipoRentabilidade) {
		if(tipoRentabilidade.equals(TipoRentabilidade.PRE)) {
			return new CalculadoraPre();
		}
		if(tipoRentabilidade.equals(TipoRentabilidade.IPCA)) {
			return new CalculadoraIPCA();
		}
		if(tipoRentabilidade.equals(TipoRentabilidade.CDI)) {
			return new CalculadoraCDI();
		}
		return null;
	}
}
