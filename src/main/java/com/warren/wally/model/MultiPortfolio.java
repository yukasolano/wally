package com.warren.wally.model;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiPortfolio {
	
	@Autowired
	private Portfolio portfolio;
	
	public double getVariacaoMensal(LocalDate dataRef) {
		
		LocalDate mesAnterior = dataRef.minusMonths(1);
		return getVariacao(mesAnterior, dataRef);
	}
	
	public double getVariacaoAnual(LocalDate dataRef) {
		LocalDate anoAnterior = dataRef.minusYears(1);
		return getVariacao(anoAnterior, dataRef);
	}
	
	public double getVariacao(LocalDate start, LocalDate end) {
		double valorStart = portfolio.getAccrual(start);
		double valorEnd = portfolio.getAccrual(end);
		return (valorEnd - valorStart)/valorStart;
	}

}
