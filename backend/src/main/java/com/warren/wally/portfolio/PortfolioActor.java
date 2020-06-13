package com.warren.wally.portfolio;

import com.warren.wally.grafico.GraficoMultiDados;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.investimento.DividendoVO;
import com.warren.wally.model.investimento.Investimento;
import com.warren.wally.model.investimento.InvestimentoResolver;
import com.warren.wally.model.investimento.ProdutoRVActor;
import com.warren.wally.model.investimento.ProdutoVO;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.MovimentacaoRepository;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import com.warren.wally.model.investimento.repository.ProdutoRepository;
import com.warren.wally.utils.BussinessDaysCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    @Resource
    private BussinessDaysCalendar bussinessDaysCalendar;

    private List<ProdutoEntity> produtosBase;

    private Map<LocalDate, PortfolioVO> mapDiaPortfolio = new HashMap<>();

    @PostConstruct
    public List<PortfolioVO> calculatePortfoliosForToday() {
        return getPortfolios(LocalDate.now());
    }

    public List<PortfolioVO> getPortfolios(LocalDate dateRef) {
        LocalDate limitDate = bussinessDaysCalendar.getPreviousWorkDay(dateRef);
        LocalDate data = bussinessDaysCalendar.getNextWorkDay(limitDate.minusYears(1));
        List<PortfolioVO> portfolios = new ArrayList<>();
        PortfolioVO vo = null;
        while (data.isBefore(limitDate) || data.isEqual(limitDate)) {
            vo = run(data, vo);
            portfolios.add(vo);
            data = bussinessDaysCalendar.getNextWorkDay(data.plusDays(14));
        }
        return portfolios;
    }

    public PortfolioVO run(LocalDate dataRef, PortfolioVO portfolioOntem) {
        System.out.println(dataRef.toString());
        PortfolioVO portfolioVO = mapDiaPortfolio.get(dataRef);


        if (portfolioVO == null) {
            List<ProdutoVO> produtos;
            if(portfolioOntem == null) {
                produtos = getProdutos(dataRef);
            } else {
                produtos = getProdutosAcum(dataRef, portfolioOntem.getProdutos());
            }

            portfolioVO = PortfolioVO.builder().dataRef(dataRef).produtos(produtos)
                    .accrual(calcAccrual(produtos)).valorAplicado(calcValorAplicado(produtos)).build();
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

    private double calcValorAplicado(List<ProdutoVO> produtos) {
        return produtos.stream().mapToDouble(ProdutoVO::getValorAplicado).sum();
    }

    private List<ProdutoEntity> getProdutosBase() {
        if(produtosBase == null) {
            produtosBase = repository.findAll();
        }
        return produtosBase;
    }

    private List<ProdutoVO> getProdutos(LocalDate dataRef) {
        List<ProdutoVO> produtos = new ArrayList<>();


        getProdutosBase().forEach(p -> {
            long start = System.currentTimeMillis();
            try {
                Investimento invest = investimentoResolver.resolve(p.getTipoInvestimento());
                produtos.add(invest.calc(dataRef, p));
            } catch (Exception e) {
                System.out.println("Erro no produto: " + p + ": " + e.getMessage());
            }
            System.out.println("Tempo do produto " +  p + ":" + (System.currentTimeMillis()-start));
        });
        return produtos;
    }

    private List<ProdutoVO> getProdutosAcum(LocalDate dataRef, List<ProdutoVO> produtosOntem) {
        List<ProdutoVO> produtos = new ArrayList<>();


        getProdutosBase().forEach(p -> {
            long start = System.currentTimeMillis();
            try {
                Investimento invest = investimentoResolver.resolve(p.getTipoInvestimento());
                Optional<ProdutoVO> produtoCalc = produtosOntem.stream().filter(it->it.getCodigo().equals(p.getCodigo())).findFirst();
                if(produtoCalc.isPresent()) {
                    produtos.add(invest.calcAcum(dataRef, produtoCalc.get()));
                } else {
                    produtos.add(invest.calc(dataRef, p));
                }
            } catch (Exception e) {
                System.out.println("Erro no produto: " + p + ": " + e.getMessage());
            }
            System.out.println("Tempo do produto " +  p + ":" + (System.currentTimeMillis()-start));
        });
        return produtos;
    }

    public GraficoMultiDados getDividendos(LocalDate dataRef) {

        GraficoSeries graficoSeries = new GraficoSeries();
        LocalDate anoAnterior = dataRef.minusYears(1);
        run(dataRef, null).getProdutos().forEach(it -> {
            List<DividendoVO> dividendos = it.getDividendos().stream().filter(div -> div.getData().isAfter(anoAnterior)).collect(Collectors.toList());
            for (DividendoVO dividendo : dividendos) {
                graficoSeries.addDado(it.getCodigo(), YearMonth.from(dividendo.getData()).toString(), dividendo.getValorUnitario() * dividendo.getQuantidade());
            }
        });

        return graficoSeries.transforma();
    }

    public double ajustePorDia(LocalDate dataRef) {
        double dividendos = movimentacaoRepository.findByTipoMovimentoAndData(TipoMovimento.DIVIDENDO, dataRef).stream().mapToDouble(it-> it.getValorUnitario()* it.getQuantidade()).sum();
        double compra = movimentacaoRepository.findByTipoMovimentoAndData(TipoMovimento.COMPRA, dataRef).stream().mapToDouble(it-> it.getValorUnitario()* it.getQuantidade()).sum();
        double resgate = movimentacaoRepository.findByTipoMovimentoAndData(TipoMovimento.RESGATE, dataRef).stream().mapToDouble(it-> it.getValorUnitario()* it.getQuantidade()).sum();
        return compra - resgate - dividendos;
    }

    public List<MovimentacaoEntity> getExtrato(LocalDate dataRef) {
        return movimentacaoRepository.findByDataBetweenOrderByData(dataRef.minusYears(1), dataRef);
    }

}
