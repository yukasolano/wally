package com.warren.wally.model;

import java.time.LocalDate;

public class CalculadoraIPCA implements Calculadora {

	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TipoRentabilidade getTipoRentabilidade() {
		return TipoRentabilidade.IPCA;
	}

}
