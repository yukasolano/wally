package com.warren.wally.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
import com.warren.wally.model.investimento.ProdutoRVInfoVO;
import com.warren.wally.model.investimento.ProdutoVO;
import com.warren.wally.model.investimento.ProdutosVO;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.portfolio.MultiPortfolio;
import com.warren.wally.portfolio.PortfolioActor;
import com.warren.wally.portfolio.PortfolioVO;
import com.warren.wally.repository.DividendoEntity;
import com.warren.wally.repository.DividendoRepository;
import com.warren.wally.repository.MovimentacaoEntity;
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

	@PostMapping(value = "produtos/renda-fixa")
	public ProdutoInfoVO salvar(
			@RequestBody ProdutoInfoVO produto) {
		ProdutoEntity novoProduto = new ProdutoEntity();
		novoProduto.setCorretora(produto.getCorretora());
		novoProduto.setInstituicao(produto.getInstituicao());
		novoProduto.setTipoInvestimento(produto.getTipoInvestimento());
		novoProduto.setTipoRentabilidade(produto.getTipoRentabilidade());
		novoProduto.setVencimento(produto.getDtVencimento());
		novoProduto.setDtAplicacao(produto.getDtAplicacao());
		novoProduto.setTaxa(produto.getTaxa());
		novoProduto.setValorAplicado(produto.getValorAplicado());
		produtoRepository.save(novoProduto);

		return produto;
	}
	
	@PostMapping(value = "produtos/arquivo-renda-fixa")
	public ProdutoInfoVO salvarArquivo(
			@RequestBody MultipartFile arquivo) {
		produtoRepository.deleteAll();
		fileUploadResolver.resolve(TypeFile.INVESTIMENTOS).read(arquivo);
		
		
		return null;
	}
	
	@PostMapping(value = "produtos/renda-variavel")
	public ProdutoRVInfoVO criarProdutoRV(
			@RequestBody ProdutoRVInfoVO produto) {
		if(produto.getTipo().equals("dividendo")) {
			DividendoEntity dividendo = new DividendoEntity();
			dividendo.setCodigo(produto.getCodigo());
			dividendo.setData(produto.getData());
			dividendo.setQuantidade(produto.getQuantidade());
			dividendo.setValorUnitario(produto.getValorUnitario());
			dividendoRepository.save(dividendo);
		} else {
			MovimentacaoEntity mov = new MovimentacaoEntity();
			mov.setCodigo(produto.getCodigo());
			mov.setData(produto.getData());
			mov.setQuantidade(produto.getQuantidade());
			mov.setValorUnitario(produto.getValorUnitario());
			movimentacaoRepository.save(mov);
		}
		return produto;
	}
	
	@PostMapping(value = "produtos/arquivo-renda-variavel")
	public ProdutoRVInfoVO salvarArquivoRV(
			@RequestBody MultipartFile arquivo) {
		movimentacaoRepository.deleteAll();
		dividendoRepository.deleteAll();
		fileUploadResolver.resolve(TypeFile.MOVIMENTOS).read(arquivo);
		
		
		return null;
	}

}
