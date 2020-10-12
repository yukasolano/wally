package com.warren.wally.controller;

import com.warren.wally.grafico.GraficoDados;
import com.warren.wally.grafico.GraficoMultiDados;
import com.warren.wally.grafico.GraficoTransformador;
import com.warren.wally.grafico.GraficosVO;
import com.warren.wally.model.calculadora.TipoRentabilidade;
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
    private BussinessDaysCalendar bc;

    @RequestMapping("/liquidez")
    public GraficoDados liquidez(@PathParam("date") String date) {
        PortfolioVO portfolio = portfolioActor.run(bc.getPreviousWorkDay(LocalDate.parse(date)), null);
        return graficoTransformador.transforma(portfolio.getLiquidez());
    }

    @RequestMapping("/instituicoes")
    public GraficoDados instituicoes(@PathParam("date") String date) {
        PortfolioVO portfolio = portfolioActor.run(bc.getPreviousWorkDay(LocalDate.parse(date)), null);
        return graficoTransformador.transforma(portfolio.getPorInstituicoes());
    }

    @RequestMapping("/dividendos")
    public GraficoMultiDados dividendos(@PathParam("date") String date, @PathParam("tipo") TipoRentabilidade tipo) {
        return portfolioActor.getDividendos(bc.getPreviousWorkDay(LocalDate.parse(date)), tipo);
    }

    @RequestMapping("/evolucao")
    public GraficoMultiDados evolucao(@PathParam("date") String date) {
        return multiportfolio.calculaEvolucao(bc.getPreviousWorkDay(LocalDate.parse(date)));
    }

    @RequestMapping("/rentabilidade")
    public GraficoMultiDados rentabilidade(@PathParam("date") String date) {
        return multiportfolio.getRentabilidade(bc.getPreviousWorkDay(LocalDate.parse(date)));
    }

    @RequestMapping("/resumo")
    public GraficosVO index(@PathParam("date") String date) {
        PortfolioVO portfolio = portfolioActor.run(bc.getPreviousWorkDay(LocalDate.parse(date)), null);
        GraficosVO graficos = new GraficosVO();
        graficos.setPatrimonioTotal(portfolio.getAccrual());
        graficos.setVariacao(multiportfolio.calculaVariacoes(portfolio));
        graficos.setProporcao(graficoTransformador.transforma(portfolio.getProporcoes()));
        return graficos;
    }
}
