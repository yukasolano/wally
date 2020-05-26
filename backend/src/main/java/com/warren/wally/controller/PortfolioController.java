package com.warren.wally.controller;

import com.warren.wally.grafico.GraficoDados;
import com.warren.wally.grafico.GraficoMultiDados;
import com.warren.wally.grafico.GraficoTransformador;
import com.warren.wally.grafico.GraficosVO;
import com.warren.wally.portfolio.MultiPortfolio;
import com.warren.wally.portfolio.PortfolioActor;
import com.warren.wally.portfolio.PortfolioVO;
import com.warren.wally.utils.BussinessDaysCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private MultiPortfolio multiportfolio;

    @Autowired
    private PortfolioActor portfolioActor;

    @Resource
    private GraficoTransformador graficoTransformador;

    @Resource
    private BussinessDaysCalendar bussinessDaysCalendar;

    @RequestMapping("/liquidez")
    public GraficoDados liquidez(@PathParam("date") String date) {
        PortfolioVO portfolio = portfolioActor.run(bussinessDaysCalendar.getPreviousWorkDay(LocalDate.parse(date)));
        return graficoTransformador.transforma(portfolio.getLiquidez());
    }

    @RequestMapping("/instituicoes")
    public GraficoDados instituicoes(@PathParam("date") String date) {
        PortfolioVO portfolio = portfolioActor.run(bussinessDaysCalendar.getPreviousWorkDay(LocalDate.parse(date)));
        return graficoTransformador.transforma(portfolio.getPorInstituicoes());
    }

    @RequestMapping("/dividendos")
    public GraficoMultiDados dividendos(@PathParam("date") String date) {
        return portfolioActor.getDividendos(bussinessDaysCalendar.getPreviousWorkDay(LocalDate.parse(date)));
    }

    @RequestMapping("/evolucao")
    public GraficoMultiDados evolucao(@PathParam("date") String date) {
        return multiportfolio.calculaEvolucao(bussinessDaysCalendar.getPreviousWorkDay(LocalDate.parse(date)));
    }

    @RequestMapping("/portfolios")
    public List<PortfolioVO> portfolios() {
        return portfolioActor.getPortfolios();
    }

    @RequestMapping("/rentabilidade")
    public GraficoMultiDados rentabilidade(@PathParam("date") String date) {
        return multiportfolio.getRentabilidade(LocalDate.parse(date));
    }

    @RequestMapping("/resumo")
    public GraficosVO index(@PathParam("date") String date) {
        PortfolioVO portfolio = portfolioActor.run(LocalDate.parse(date));
        GraficosVO graficos = new GraficosVO();
        graficos.setPatrimonioTotal(portfolio.getAccrual());
        graficos.setVariacao(multiportfolio.calculaVariacoes(portfolio));
        graficos.setProporcao(graficoTransformador.transforma(portfolio.getProporcoes()));
        return graficos;
    }
}