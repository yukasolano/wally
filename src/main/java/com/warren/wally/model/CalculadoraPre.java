package com.warren.wally.model;

import java.time.LocalDate;

import com.warren.wally.model.calculadora.Calculadora;
import com.warren.wally.model.calculadora.TipoRentabilidade;

public class CalculadoraPre implements Calculadora {

	@Override
	public double calculaVPBruto(double valorAplicado, double taxa, LocalDate dtAplicacao, LocalDate dtRef) {
		BussinessDaysCalendar bc = new BussinessDaysCalendar();
		long du = bc.getDu(dtAplicacao, dtRef);
		double fte = Math.pow(1 + taxa, du / 252.0);
		return valorAplicado * fte;
	}

	@Override
	public TipoRentabilidade getTipoRentabilidade() {
		return TipoRentabilidade.PRE;
	}

}
