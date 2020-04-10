package com.warren.wally.portfolio;

import com.warren.wally.grafico.GraficoMultiDados;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.investimento.DividendoVO;
import com.warren.wally.model.investimento.Investimento;
import com.warren.wally.model.investimento.InvestimentoResolver;
import com.warren.wally.model.investimento.ProdutoRVActor;
import com.warren.wally.model.investimento.ProdutoVO;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;
import com.warren.wally.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class PortfolioActor {

    @Resource
    private ProdutoRVActor produtoRVActor;

    @Autowired
    private ProdutoRepository repository;

    @Resource
    private CalculadoraResolver calculadoraResolver;

    @Resource
    private InvestimentoResolver investimentoResolver;

    @Resource
    private MovimentacaoRepository movimentacaoRepository;

    private Map<LocalDate, PortfolioVO> mapDiaPortfolio = new HashMap<>();

    public PortfolioVO run(LocalDate dataRef) {
        PortfolioVO portfolioVO = mapDiaPortfolio.get(dataRef);

        if (portfolioVO == null) {
            List<ProdutoVO> produtos = getProdutos(dataRef);
            portfolioVO = PortfolioVO.builder().dataRef(dataRef).produtos(produtos)
                    .accrual(calcAccrual(produtos)).build();
            mapDiaPortfolio.put(dataRef, portfolioVO);
        }
        return portfolioVO;
    }

    public void limpaMapa() {
        mapDiaPortfolio.clear();
    }

    private double calcAccrual(List<ProdutoVO> produtos) {
        return produtos.stream().mapToDouble(ProdutoVO::getValorPresente).sum();
    }

    private List<ProdutoVO> getProdutos(LocalDate dataRef) {
        List<ProdutoVO> produtos = new ArrayList<>();

        repository.findAll().forEach(p -> {
            try {
                Investimento invest = investimentoResolver.resolve(p.getTipoInvestimento());
                produtos.add(invest.calc(dataRef, p));
            } catch (Exception e) {
                System.out.println("Erro no produto: " + p + ": " + e.getMessage());
            }
        });
        return produtos;
    }

    public GraficoMultiDados getDividendos(LocalDate dataRef) {

        Map<String, Map<String, Double>> todos = new HashMap<>();

        LocalDate anoAnterior = dataRef.minusYears(1);
        mapDiaPortfolio.get(dataRef).getProdutos().stream().forEach(it -> {
            Map<String, Double> anoDividendo = new TreeMap<>();
            List<DividendoVO> dividendos = it.getDividendos().stream().filter(div -> div.getData().isAfter(anoAnterior)).collect(Collectors.toList());
            for (DividendoVO dividendo : dividendos) {
                Double valor = anoDividendo.getOrDefault(YearMonth.from(dividendo.getData()).toString(), 0.0);
                anoDividendo.put(YearMonth.from(dividendo.getData()).toString(), valor + dividendo.getValorUnitario() * dividendo.getQuantidade());
            }
            todos.put(it.getCodigo(), anoDividendo);

        });

        double[][] dados = new double[todos.size()][12];

        String[] series = new String[todos.size()];
        String[] labels = new String[12];

        YearMonth mesAno = YearMonth.from(dataRef);
        for (int i = 11; i >= 0; i--) {
            labels[i] = mesAno.toString();
            mesAno = mesAno.minusMonths(1);
        }

        int row = 0;
        for (String codigo : todos.keySet()) {
            series[row] = codigo;
            for (int col = 0; col < 12; col++) {
                String label = labels[col];
                try {
                    dados[row][col] = todos.get(codigo).get(label);
                } catch (Exception e) {
                    dados[row][col] = 0.0;
                }
            }

            row++;
        }

        return new GraficoMultiDados(labels, series, dados);
    }

    public List<MovimentacaoEntity> getExtrato(LocalDate dataRef) {
        return movimentacaoRepository.findByDataBetweenOrderByData(dataRef.minusYears(1), dataRef);
    }

}
