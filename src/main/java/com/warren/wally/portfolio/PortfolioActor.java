package com.warren.wally.portfolio;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warren.wally.grafico.GraficoMultiDados;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.investimento.Investimento;
import com.warren.wally.model.investimento.InvestimentoResolver;
import com.warren.wally.model.investimento.ProdutoRFVO;
import com.warren.wally.model.investimento.ProdutoRVActor;
import com.warren.wally.model.investimento.ProdutoRVVO;
import com.warren.wally.repository.ProdutoRepository;

@Component
public class PortfolioActor {

	@Resource
	private ProdutoRVActor actorFII;

	@Autowired
	private ProdutoRepository repository;

	@Resource
	private CalculadoraResolver calculadoraResolver;

	@Resource
	private InvestimentoResolver investimentoResolver;

	public PortfolioVO run(LocalDate dataRef) {
		List<ProdutoRFVO> produtosRF = getProdutosRF(dataRef);
		List<ProdutoRVVO> produtosRV = getProdutosRV(dataRef);

		return PortfolioVO.builder().dataRef(dataRef).produtosRF(produtosRF).produtosRV(produtosRV)
				.accrual(calcAccrual(produtosRF, produtosRV)).build();
	}

	public double calcAccrual(List<ProdutoRFVO> produtosRF, List<ProdutoRVVO> produtosRV) {
		double accrual = 0.0;
		accrual += produtosRF.stream().mapToDouble(it -> it.getValorPresente()).sum();
		accrual += produtosRV.stream().mapToDouble(it -> it.getValorPresente()).sum();
		return accrual;
	}

	public List<ProdutoRFVO> getProdutosRF(LocalDate dataRef) {
		List<ProdutoRFVO> produtos = new ArrayList<>();

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

	public List<ProdutoRVVO> getProdutosRV(LocalDate dataRef) {
		return actorFII.run(dataRef);
	}

	public GraficoMultiDados getDividendos(LocalDate dataRef) {

		Map<String, Map<String, Double>> todos = new HashMap<>();

		actorFII.getDividendos(dataRef).stream().forEach(dividendo -> {
			todos.putIfAbsent(dividendo.getCodigo(), new TreeMap<String, Double>());
			todos.get(dividendo.getCodigo()).put(YearMonth.from(dividendo.getData()).toString(),
					dividendo.getValorUnitario() * dividendo.getQuantidade());
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

}
