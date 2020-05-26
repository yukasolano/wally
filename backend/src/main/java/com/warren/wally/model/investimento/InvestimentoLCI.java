package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.Calculadora;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import com.warren.wally.utils.BussinessDaysCalendar;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

@Component
public class InvestimentoLCI extends InvestimentoAbstract {

    @Resource
    private CalculadoraResolver calculadoraResolver;

    @Resource
    private BussinessDaysCalendar bc;

    @Override
    public TipoInvestimento getTipoInvestimento() {
        return TipoInvestimento.LCI;
    }

    @Override
    public ProdutoVO calc(LocalDate dataRef,
                          ProdutoEntity entity) {

        ProdutoRFVO vo = convertVO(entity, dataRef);

        Calculadora calc = calculadoraResolver.resolve(vo.getTipoRentabilidade());

        double VPBruto = calc.calculaVPBruto(vo.getValorAplicado(), vo.getTaxa(), vo.getDtAplicacao(),
                vo.getDataReferencia());
        vo.setValorPresente(getValorPresente(vo, VPBruto));
        vo.setRentabilidadeLiquida(getRentabilidadeLiquida(vo));
        vo.setTaxaAnualLiquida(getTaxaAnualLiquida(vo));
        vo.setTaxaMensalLiquida(getTaxaMensalLiquida(vo));
        return vo;
    }

    protected double getValorPresente(ProdutoRFVO vo,
                                      double VPBruto) {
        return VPBruto;
    }
}
