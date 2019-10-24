package com.warren.wally.model.calculadora;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class CalculadoraResolver {
	
	@Resource
	private CalculadoraCDIStrategy calculadoraCdi;
	
	public Calculadora resolve(TipoRentabilidade tipoRentabilidade) {
		if(tipoRentabilidade.equals(TipoRentabilidade.CDI)) {
			return calculadoraCdi;
		}
		return calculadoraCdi;
	}

}
