package com.warren.wally.portfolio;

import com.warren.wally.model.investimento.ProdutoVO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class PortfolioVO {

    private List<ProdutoVO> produtos;

    private double accrual;

    private double valorAplicado;

    private LocalDate dataRef;

    public Map<String, Double> getProporcoes() {
        Map<String, Double> proporcoes = produtos.stream()
                .collect(Collectors.groupingBy(produto -> produto.getTipoRentabilidade().toString(),
                        Collectors.reducing(0.0, ProdutoVO::getValorPresente, Double::sum)));
        return sortedByKey(proporcoes);
    }

    public Map<String, Double> getPorInstituicoes() {
        Map<String, Double> proporcoes = produtos.stream()
                .collect(Collectors.groupingBy(ProdutoVO::getInstituicao,
                        Collectors.reducing(0.0, ProdutoVO::getValorPresente, Double::sum)));
        return sortedByKey(proporcoes);
    }

    public Map<String, Double> getLiquidez() {
        Map<String, Double> proporcoes = produtos.stream()
                .collect(Collectors.groupingBy(ProdutoVO::getAnoVencimento,
                        Collectors.reducing(0.0, ProdutoVO::getValorPresente, Double::sum)));

        return sortedByKey(proporcoes);
    }

    private Map<String, Double> sortedByKey(final Map<String, Double> inputMap) {
        return inputMap.entrySet().stream().filter(it -> it.getValue() > 0d).sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }
}
