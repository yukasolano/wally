package com.warren.wally.portfolio;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.grafico.GraficoMultiDados;
import com.warren.wally.repository.ProdutoRepository;

@Component
public class MultiPortfolio {
	
	@Autowired
	private PortfolioActor portfolioActor;

	public VariacaoVO calculaVariacoes(PortfolioVO portfolio) {

		VariacaoVO variacao = new VariacaoVO();
		
		// variacao mensal
		PortfolioVO portfolioMesAnterior = portfolioActor.run(portfolio.getDataRef().minusMonths(1));
		variacao.setMensalAbsoluto(portfolio.getAccrual() - portfolioMesAnterior.getAccrual());
		variacao.setMensalPorcentagem(variacao.getMensalAbsoluto() / portfolioMesAnterior.getAccrual());

		// variacao anual
		PortfolioVO portfolioAnoAnterior = portfolioActor.run(portfolio.getDataRef().minusYears(1));
		variacao.setAnualAbsoluto(portfolio.getAccrual() - portfolioAnoAnterior.getAccrual());
		variacao.setAnualPorcentagem(variacao.getAnualAbsoluto() / portfolioAnoAnterior.getAccrual());

		return variacao;
	}
	
	public GraficoMultiDados calculaEvolucao(PortfolioVO portfolio) {

		double[][] dados = new double[1][12];

		String[] series = new String[1];
		String[] labels = new String[12];

		YearMonth mesAno = YearMonth.from(portfolio.getDataRef());
		for (int i = 11; i >= 0; i--) {
			labels[i] = mesAno.toString();
			mesAno = mesAno.minusMonths(1);
		}

		series[0] = "Patrimônio";
		for (int col = 0; col < 12; col++) {
			String label = labels[col];
			try {
				LocalDate data = LocalDate.parse(label+ "-" + "01");
				data.withDayOfMonth(data.lengthOfMonth());
				dados[0][col] = portfolioActor.run(data.withDayOfMonth(data.lengthOfMonth())).getAccrual();
			} catch (Exception e) {
				dados[0][col] = 0.0;
			}
		}	

		return new GraficoMultiDados(labels, series, dados);
	}
}
