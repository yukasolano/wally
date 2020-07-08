package com.warren.wally.portfolio;

import com.warren.wally.grafico.GraficoMultiDados;
import com.warren.wally.model.investimento.ProdutoRVVO;
import com.warren.wally.model.investimento.ProdutoVO;
import com.warren.wally.utils.BussinessDaysCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class MultiPortfolio {

    @Autowired
    private PortfolioActor portfolioActor;

    @Resource
    private BussinessDaysCalendar bussinessDaysCalendar;

    public VariacaoVO calculaVariacoes(PortfolioVO portfolio) {

        VariacaoVO variacao = new VariacaoVO();

        // variacao mensal
        PortfolioVO portfolioMesAnterior = portfolioActor.run(bussinessDaysCalendar.getNextWorkDay(portfolio.getDataRef().minusMonths(1)), null);
        variacao.setMensalAbsoluto(portfolio.getAccrual() - portfolioMesAnterior.getAccrual());
        if (portfolioMesAnterior.getAccrual() > 0) {
            variacao.setMensalPorcentagem(variacao.getMensalAbsoluto() / portfolioMesAnterior.getAccrual());
        }

        // variacao anual
        PortfolioVO portfolioAnoAnterior = portfolioActor.run(bussinessDaysCalendar.getNextWorkDay(portfolio.getDataRef().minusYears(1)), null);
        variacao.setAnualAbsoluto(portfolio.getAccrual() - portfolioAnoAnterior.getAccrual());
        if (portfolioAnoAnterior.getAccrual() > 0) {
            variacao.setAnualPorcentagem(variacao.getAnualAbsoluto() / portfolioAnoAnterior.getAccrual());
        }

        return variacao;
    }

    public GraficoMultiDados calculaEvolucao(LocalDate date) {
        GraficoSeries series = new GraficoSeries();

        List<PortfolioVO> portfolios = portfolioActor.getPortfolios(date, 1);
        for(PortfolioVO portfolio : portfolios) {
            series.addDado("Patrimônio", portfolio.getDataRef().toString(), portfolio.getAccrual());
            series.addDado("Aplicação", portfolio.getDataRef().toString(), portfolio.getValorAplicado());
        }

        return series.transforma();
    }

    public GraficoMultiDados getRentabilidade(LocalDate date) {
        GraficoSeries series = new GraficoSeries();
        double acumulado = 1d;
        double lastVP = 0d;

        List<PortfolioVO> portfolios = portfolioActor.getPortfolios(date, 1);
        for(PortfolioVO portfolio : portfolios) {
            double r = lastVP == 0d ? 1d : (portfolio.getAccrual() - lastVP - portfolioActor.ajustePorDia(portfolio.getDataRef()))/ lastVP + 1d;
            lastVP = portfolio.getAccrual();
            acumulado *= r;
            series.addDado("Rentabilidade", portfolio.getDataRef().toString(), acumulado);
        }
        return series.transforma();
    }

    public GraficoMultiDados calculaEvolucao(LocalDate date, String codigo) {
        GraficoSeries series = new GraficoSeries();

        List<PortfolioVO> portfolios = portfolioActor.getPortfolios(date, 1);
        for(PortfolioVO portfolio : portfolios) {
            Optional<ProdutoVO> produto = portfolio.getProdutos().stream().filter(it -> it.getCodigo().equals(codigo)).findFirst();
            double VP = produto.isPresent() ?  produto.get().getValorPresente() : 0d;
            double VA = produto.isPresent() ?  produto.get().getValorAplicado() : 0d;
            series.addDado("Patrimônio", portfolio.getDataRef().toString(), VP);
            series.addDado("Aplicação", portfolio.getDataRef().toString(), VA);
        }

        return series.transforma();
    }

    public GraficoMultiDados getRentabilidade(LocalDate date, String codigo) {
        GraficoSeries series = new GraficoSeries();
        double acumulado = 1d;
        double lastVP = 0d;

        List<PortfolioVO> portfolios = portfolioActor.getPortfolios(date, 1);
        for(PortfolioVO portfolio : portfolios) {
            Optional<ProdutoVO> produto = portfolio.getProdutos().stream().filter(it -> it.getCodigo().equals(codigo)).findFirst();
            double VP = produto.isPresent() ?  produto.get().getValorPresente() : 0d;

            double r = lastVP == 0d ? 1d : (VP - lastVP - portfolioActor.ajustePorDia(portfolio.getDataRef(), codigo))/ lastVP + 1d;
            lastVP = VP;
            acumulado *= r;
            series.addDado("Rentabilidade", portfolio.getDataRef().toString(), acumulado);
        }
        return series.transforma();
    }

    public GraficoMultiDados getCotacao(LocalDate date,
                                        String codigo) {

        GraficoSeries series = new GraficoSeries();

        List<PortfolioVO> portfolios = portfolioActor.getPortfolios(date, 1);
        for(PortfolioVO portfolio : portfolios) {
            Optional<ProdutoRVVO> produto = portfolio.getProdutos().stream().filter(it -> it.getCodigo().equals(codigo)).map(it -> (ProdutoRVVO)it).findFirst();
            double cotacao = produto.map(ProdutoRVVO::getCotacao).orElse(0d);
            series.addDado("Cotação", portfolio.getDataRef().toString(), cotacao);
        }

        return series.transforma();
    }
}

