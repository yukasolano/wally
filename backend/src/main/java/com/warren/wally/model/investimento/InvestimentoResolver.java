package com.warren.wally.model.investimento;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InvestimentoResolver {

    @Resource
    private InvestimentoCDB investimentoCDB;

    @Resource
    private InvestimentoLCI investimentoLCI;

    @Resource
    private InvestimentoDebenture investimentoDebenture;

    @Resource
    private InvestimentoLC investimentoLC;

    @Resource
    private InvestimentoTESOURO investimentoTESOURO;

    @Resource
    private InvestimentoFII investimentoFII;

    @Resource
    private InvestimentoACAO investimentoACAO;

    public Investimento resolve(TipoInvestimento tipoInvestimento) {
        if (tipoInvestimento.equals(TipoInvestimento.CDB)) {
            return investimentoCDB;
        }
        if (tipoInvestimento.equals(TipoInvestimento.LCI)) {
            return investimentoLCI;
        }
        if (tipoInvestimento.equals(TipoInvestimento.DEBENTURE)) {
            return investimentoDebenture;
        }
        if (tipoInvestimento.equals(TipoInvestimento.LC)) {
            return investimentoLC;
        }
        if (tipoInvestimento.equals(TipoInvestimento.TESOURO)) {
            return investimentoTESOURO;
        }
        if (tipoInvestimento.equals(TipoInvestimento.FII)) {
            return investimentoFII;
        }
        if (tipoInvestimento.equals(TipoInvestimento.ACAO)) {
            return investimentoACAO;
        }
        throw new RuntimeException("Tipo de investimento " + tipoInvestimento + " não existe.");
    }

}
