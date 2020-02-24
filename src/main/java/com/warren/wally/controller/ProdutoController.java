package com.warren.wally.controller;

import static com.warren.wally.utils.DateUtils.dateOf;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.warren.wally.file.ExportedFile;
import com.warren.wally.file.FileExporterInvestimentos;
import com.warren.wally.file.FileExporterResolver;
import com.warren.wally.file.FileUploadResolver;
import com.warren.wally.file.TypeFile;
import com.warren.wally.grafico.GraficoDados;
import com.warren.wally.grafico.GraficoTransformador;
import com.warren.wally.grafico.GraficosVO;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.ProdutoRVInfoVO;
import com.warren.wally.model.investimento.ProdutoRFVO;
import com.warren.wally.model.investimento.ProdutosVO;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
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

	private LocalDate data = LocalDate.of(2020, 01, 31); // LocalDate.now();
	
	@Autowired
	private FileExporterResolver fileExporterResolver;

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
		graficos.setDividendos(portfolioActor.getDividendos(data));
		graficos.setEvolucao(multiportfolio.calculaEvolucao(portfolio));
		
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
			dividendoRepository.save(new DividendoEntity(TipoInvestimento.FII, TipoMovimento.DIVIDENDO,
					produto.getData(), produto.getCodigo(), produto.getQuantidade(), produto.getValorUnitario()));
		} else {
			movimentacaoRepository.save(new MovimentacaoEntity(TipoInvestimento.FII, TipoMovimento.COMPRA,
					produto.getData(), produto.getCodigo(), produto.getQuantidade(), produto.getValorUnitario()));
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
	
	@GetMapping(value = "produtos/arquivo-renda-fixa/download")
	public ResponseEntity exportaArquivoRF() {
		ExportedFile resource = fileExporterResolver.resolve(TypeFile.INVESTIMENTOS).export();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ resource.getFilename())
		        .contentLength(resource.getFile().contentLength())
		        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
		        .body(resource.getFile());
		
	}
	
	@GetMapping(value = "produtos/arquivo-renda-variavel/download")
	public ResponseEntity exportaArquivoRVF() {
		ExportedFile resource = fileExporterResolver.resolve(TypeFile.MOVIMENTOS).export();

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ resource.getFilename())
		        .contentLength(resource.getFile().contentLength())
		        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
		        .body(resource.getFile());
		
	}

}
