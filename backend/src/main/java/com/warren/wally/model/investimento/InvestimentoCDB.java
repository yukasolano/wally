package com.warren.wally.model.investimento;

import com.warren.wally.utils.Leao;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class InvestimentoCDB extends InvestimentoAbstract {

    @Resource
    private Leao leao;

    @Override
    public TipoInvestimento getTipoInvestimento() {
        return TipoInvestimento.CDB;
    }

    @Override
    protected double getValorPresente(ProdutoRFVO vo,
                                      double VPBruto) {
        double ir = leao.getIR(VPBruto - vo.getValorAplicado(), vo.getDtAplicacao(), vo.getDataReferencia());
        return VPBruto - ir;
    }

}
