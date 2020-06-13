package com.warren.wally.model.investimento;

import org.springframework.stereotype.Component;

@Component
public class InvestimentoLCI extends InvestimentoAbstract {

    @Override
    public TipoInvestimento getTipoInvestimento() {
        return TipoInvestimento.LCI;
    }

    @Override
    protected double getValorPresente(ProdutoRFVO vo,
                                      double VPBruto) {
        return VPBruto;
    }
}
