package com.warren.wally.model;


public class CalculadoraFactory {
	public static Calculadora getCalculadora(TipoRentabilidade tipoRentabilidade) {
		if(tipoRentabilidade.equals(TipoRentabilidade.PRE)) {
			return new CalculadoraPre();
		}
		return null;
	}
}
