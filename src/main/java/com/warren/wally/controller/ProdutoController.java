package com.warren.wally.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.warren.wally.file.FileUploadResolver;
import com.warren.wally.file.TypeFile;
import com.warren.wally.grafico.GraficoDados;
import com.warren.wally.grafico.GraficoTransformador;
import com.warren.wally.grafico.GraficosVO;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.ProdutoVO;
import com.warren.wally.model.investimento.ProdutosVO;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.portfolio.MultiPortfolio;
import com.warren.wally.portfolio.PortfolioActor;
import com.warren.wally.portfolio.PortfolioVO;
import com.warren.wally.repository.DividendoRepository;
import com.warren.wally.repository.MovimentacaoRepository;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;

@RestController
public class ProdutoController {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private MultiPortfolio multiportfolio;

	@Autowired
	private PortfolioActor portfolioActor;

	@Autowired
	private FileUploadResolver fileUploadResolver;

	@Autowired
	private MovimentacaoRepository movimentacaoRepository;

	@Autowired
	private DividendoRepository dividendoRepository;

	private LocalDate data = LocalDate.of(2019, 07, 01); // LocalDate.now();

	@RequestMapping("/portfolio-graficos")
	public GraficosVO index(Model model) {
		GraficoTransformador graficoTransformador = new GraficoTransformador();
		PortfolioVO portfolio = portfolioActor.run(data);
		GraficosVO graficos = new GraficosVO();
		graficos.setPatrimonioTotal(portfolio.getAccrual());
		graficos.setVariacao(multiportfolio.calculaVariacoes(portfolio));
		graficos.setProporcao(graficoTransformador.transforma(portfolio.getProporcoes()));
		graficos.setProporcaoRV(graficoTransformador.transforma(portfolio.getProporcoesRV()));
		graficos.setInstituicoes(graficoTransformador.transforma(portfolio.getPorInstituicoes(), true));
		graficos.setLiquidez(graficoTransformador.transforma(portfolio.getLiquidez(), true));
		return graficos;
	}

	@RequestMapping("produtos")
	public ProdutosVO produtos(Model model) {
		PortfolioVO portfolio = portfolioActor.run(data);
		ProdutosVO vo = new ProdutosVO();
		vo.setProdutosRF(portfolio.getProdutosRF());
		vo.setProdutosRV(portfolio.getProdutosRV());
		return vo;
		
	}

	/*@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvar(@RequestParam(value = "corretora", required = false) String corretora,
			@RequestParam(value = "instituicao", required = false) String instituicao,
			@RequestParam(value = "tipoInvestimento", required = false) String tipoInvestimento,
			@RequestParam(value = "tipoRentabilidade", required = false) String tipoRentabilidade,
			@RequestParam(value = "dtVencimento", required = false) String dtVencimento,
			@RequestParam(value = "dtAplicacao", required = false) String dtAplicacao,
			@RequestParam(value = "taxa", required = false, defaultValue = "0.0") double taxa,
			@RequestParam(value = "valorAplicado", required = false, defaultValue = "0.0") double valorAplicado,
			@RequestParam(value = "arquivo", required = false) MultipartFile arquivo, Model model) {

		if (arquivo != null) {
			produtoRepository.deleteAll();
			fileUploadResolver.resolve(TypeFile.INVESTIMENTOS).read(arquivo);
		} else {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			ProdutoEntity novoProduto = new ProdutoEntity();
			novoProduto.setCorretora(corretora);
			novoProduto.setInstituicao(instituicao);
			novoProduto.setTipoInvestimento(TipoInvestimento.valueOf(tipoInvestimento));
			novoProduto.setTipoRentabilidade(TipoRentabilidade.valueOf(tipoRentabilidade));
			novoProduto.setVencimento(LocalDate.parse(dtVencimento, dtf));
			novoProduto.setDtAplicacao(LocalDate.parse(dtAplicacao, dtf));
			novoProduto.setTaxa(taxa);
			novoProduto.setValorAplicado(valorAplicado);
			produtoRepository.save(novoProduto);
		}

		PortfolioVO portfolio = portfolioActor.run(data);
		model.addAttribute("produtos", portfolio.getProdutosRF());
		model.addAttribute("produtosRV", portfolio.getProdutosRV());
		model.addAttribute("hoje", data);
		return "produtos";
	}

	@RequestMapping(value = "salvarfii", method = RequestMethod.POST)
	public String salvarFII(@RequestParam(value = "arquivo", required = true) MultipartFile arquivo, Model model) {
		movimentacaoRepository.deleteAll();
		dividendoRepository.deleteAll();
		fileUploadResolver.resolve(TypeFile.MOVIMENTOS).read(arquivo);

		PortfolioVO portfolio = portfolioActor.run(data);
		model.addAttribute("produtos", portfolio.getProdutosRF());
		model.addAttribute("produtosRV", portfolio.getProdutosRV());
		model.addAttribute("hoje", data);
		return "produtos";
	}*/

}
