package com.warren.wally.model.calculadora;

import java.time.LocalDate;

public interface Calculadora {

	double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef);
	

	TipoRentabilidade getTipoRentabilidade();
}
