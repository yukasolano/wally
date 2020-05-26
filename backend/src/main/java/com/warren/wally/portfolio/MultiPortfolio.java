package com.warren.wally.portfolio;

import com.warren.wally.grafico.GraficoMultiDados;
import com.warren.wally.utils.BussinessDaysCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;

@Component
public class MultiPortfolio {

    @Autowired
    private PortfolioActor portfolioActor;

    @Resource
    private BussinessDaysCalendar bussinessDaysCalendar;

    public VariacaoVO calculaVariacoes(PortfolioVO portfolio) {

        VariacaoVO variacao = new VariacaoVO();

        // variacao mensal
        PortfolioVO portfolioMesAnterior = portfolioActor.run(bussinessDaysCalendar.getNextWorkDay(portfolio.getDataRef().minusMonths(1)));
        variacao.setMensalAbsoluto(portfolio.getAccrual() - portfolioMesAnterior.getAccrual());
        if (portfolioMesAnterior.getAccrual() > 0) {
            variacao.setMensalPorcentagem(variacao.getMensalAbsoluto() / portfolioMesAnterior.getAccrual());
        }

        // variacao anual
        PortfolioVO portfolioAnoAnterior = portfolioActor.run(bussinessDaysCalendar.getNextWorkDay(portfolio.getDataRef().minusYears(1)));
        variacao.setAnualAbsoluto(portfolio.getAccrual() - portfolioAnoAnterior.getAccrual());
        if (portfolioAnoAnterior.getAccrual() > 0) {
            variacao.setAnualPorcentagem(variacao.getAnualAbsoluto() / portfolioAnoAnterior.getAccrual());
        }

        return variacao;
    }

    public GraficoMultiDados calculaEvolucao(LocalDate date) {

        LocalDate data = bussinessDaysCalendar.getNextWorkDay(date.minusYears(1));
        GraficoSeries series = new GraficoSeries();

        while (data.isBefore(date)) {
            PortfolioVO portfolioVO = portfolioActor.run(data);
            series.addDado("Patrimônio", data.toString(), portfolioVO.getAccrual());
            series.addDado("Aplicação", data.toString(), portfolioVO.getValorAplicado());
            data = bussinessDaysCalendar.getNextWorkDay(data.plusDays(1));
        }


        return series.transforma();
    }

    public GraficoMultiDados getRentabilidade(LocalDate date) {
        LocalDate data = bussinessDaysCalendar.getNextWorkDay(date.minusYears(1));
        GraficoSeries series = new GraficoSeries();
        double acumulado = 1d;
        double lastVP = 0d;

        while (data.isBefore(date)) {
            PortfolioVO portfolioVO = portfolioActor.run(data);
            double r = lastVP == 0d ? 1d : (portfolioVO.getAccrual() - lastVP - portfolioActor.ajustePorDia(data))/ lastVP + 1d;
            lastVP = portfolioVO.getAccrual();
            acumulado *= r;
            series.addDado("Rentabilidade", data.toString(), acumulado);
            data = bussinessDaysCalendar.getNextWorkDay(data.plusDays(1));
        }
        return series.transforma();
    }
}

