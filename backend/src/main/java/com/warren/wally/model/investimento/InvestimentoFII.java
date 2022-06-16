package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.dadosmercado.DMequitiesActor;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        List<DividendoVO> dividendos = produto.getDividendos().stream()
                .filter(it -> it.getData().isAfter(anoAnterior) &&
                        (it.getData().isBefore(produto.getDataReferencia()) || it.getData().isEqual(produto.getDataReferencia())))
                .collect(Collectors.toList());

        Map<Integer, Double> map = new HashMap<>();

        for (DividendoVO div : dividendos) {
            Integer mesAno = div.getData().getYear() * 100 + div.getData().getMonthValue();
            map.merge(mesAno, div.getValorUnitario(), (a, b) -> (a + b) / 2d);

        }
        double somaDividendos = map.values().stream().mapToDouble(Double::doubleValue).sum();
        double rentabilidade = somaDividendos / produto.getPrecoMedio();


        int quantidade = map.size();
        return quantidade == 0 ? 0 : Math.pow((1 + rentabilidade), 12.0 / quantidade) - 1;
    }

    @Override
    protected TipoRentabilidade getTipoRentabilidade() {
        return TipoRentabilidade.FII;
    }

    @Override
    protected void atualizaMovDiv(ProdutoRVVO vo,
                                MovimentacaoEntity mov) {
        DividendoVO dividendoVO = new DividendoVO();
        dividendoVO.setCodigo(mov.getCodigo());
        dividendoVO.setData(mov.getData());
        dividendoVO.setQuantidade(mov.getQuantidade());
        dividendoVO.setTipo(mov.getTipoMovimento());
        dividendoVO.setValorUnitario(mov.getValorUnitario());
        Optional<DividendoVO> div = vo.getDividendos().stream().filter(it -> it.getData().equals(mov.getData())).findFirst();
        if (div.isPresent()) {
            div.get().setQuantidade(div.get().getQuantidade() + mov.getQuantidade());
        } else {
            vo.addDividendo(dividendoVO);
        }
    }


}
