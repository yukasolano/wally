package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.dadosmercado.DMequitiesActor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvestimentoFII extends InvestimentoRVAbstract {

    @Autowired
    private DMequitiesActor dm;

    @Override
    public TipoInvestimento getTipoInvestimento() {
        return TipoInvestimento.FII;
    }

    @Override
    protected double getRentabilidadeDividendo(ProdutoRVVO produto) {

        if (produto.getQuantidade() == 0) {
            return 0d;
        }

        LocalDate anoAnterior = produto.getDataReferencia().minusYears(1);
        List<DividendoVO> dividendos = produto.getDividendos().stream().filter(it -> it.getData().isAfter(anoAnterior)).collect(Collectors.toList());
        double somaDividendos = dividendos.stream().mapToDouble(DividendoVO::getValorUnitario).sum();
        double rentabilidade = somaDividendos / produto.getPrecoMedio();

        int quantidade = dividendos.size();
        return Math.pow((1 + rentabilidade), 12.0 / quantidade) - 1;
    }

    @Override
    protected TipoRentabilidade getTipoRentabilidade() {
        return TipoRentabilidade.FII;
    }

}
