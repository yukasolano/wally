package com.warren.wally.model.investimento;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;


@Component
public class InvestimentoResolver {
	
	@Resource
	private InvestimentoCDB investimentoCDB;
	
	public Investimento resolve(TipoInvestimento tipoInvestimento) {
		if(tipoInvestimento.equals(TipoInvestimento.CDB)) {
			return investimentoCDB;
		}
		return investimentoCDB;
	}

}
