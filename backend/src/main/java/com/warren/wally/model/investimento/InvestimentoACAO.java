package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvestimentoACAO extends InvestimentoRVAbstract {

    @Override
    public TipoInvestimento getTipoInvestimento() {
        return TipoInvestimento.ACAO;
    }

    @Override
    protected double getRentabilidadeDividendo(ProdutoRVVO produto) {

        if (produto.getQuantidade() == 0) {
            return 0d;
        }

        LocalDate anoAnterior = produto.getDataReferencia().minusYears(1);
        List<DividendoVO> dividendos = produto.getDividendos().stream()
                .filter(it -> it.getData().isAfter(anoAnterior) &&
                        (it.getData().isBefore(produto.getDataReferencia()) || it.getData().isAfter(produto.getDataReferencia())))
                .collect(Collectors.toList());
        double somaDividendos = dividendos.stream().mapToDouble(DividendoVO::getValorUnitario).sum();
        return somaDividendos / produto.getPrecoMedio();
    }

    @Override
    TipoRentabilidade getTipoRentabilidade() {
        return TipoRentabilidade.ACAO;
    }

}
