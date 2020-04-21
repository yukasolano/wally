package com.warren.wally.controller;

import com.warren.wally.file.ExportedFile;
import com.warren.wally.file.FileExporterResolver;
import com.warren.wally.file.FileUploadResolver;
import com.warren.wally.file.TypeFile;
import com.warren.wally.grafico.GraficoTransformador;
import com.warren.wally.grafico.GraficosVO;
import com.warren.wally.model.cadastro.CadastroProdutoResolver;
import com.warren.wally.model.cadastro.ExtratoActor;
import com.warren.wally.model.cadastro.MovimentoInfoVO;
import com.warren.wally.model.cadastro.ProdutoRFInfoVO;
import com.warren.wally.model.cadastro.ProdutoRVInfoVO;
import com.warren.wally.model.dadosmercado.DadosMercadoActor;
import com.warren.wally.model.investimento.ProdutoRFVO;
import com.warren.wally.model.investimento.ProdutoRVVO;
import com.warren.wally.model.investimento.ProdutosVO;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.portfolio.MultiPortfolio;
import com.warren.wally.portfolio.PortfolioActor;
import com.warren.wally.portfolio.PortfolioVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

import static com.warren.wally.file.TypeFile.MOVIMENTOS;
import static com.warren.wally.file.TypeFile.PRODUTOS_RV;

@RestController
public class ProdutoController {

    @Autowired
    private MultiPortfolio multiportfolio;

    @Autowired
    private PortfolioActor portfolioActor;

    @Autowired
    private FileUploadResolver fileUploadResolver;

    @Resource
    private CadastroProdutoResolver cadastroProdutoResolver;

    @Resource
    private ExtratoActor extratoActor;

    private LocalDate data = LocalDate.of(2020, 01, 31); // LocalDate.now();

    @Autowired
    private DadosMercadoActor dadosMercadoActor;

    @Autowired
    private FileExporterResolver fileExporterResolver;

    @RequestMapping("/portfolio-graficos")
    public GraficosVO index(@PathParam("date") String date) {
        data = LocalDate.parse(date);
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
        vo.setProdutos(portfolio.getProdutos());
        vo.setExtrato(portfolioActor.getExtrato(data));
        return vo;

    }

    @PostMapping(value = "produtos/renda-fixa")
    public void criaProdutoRF(@RequestBody ProdutoRFInfoVO produto) {
        cadastroProdutoResolver.resolve(produto.getTipoInvestimento(), TipoMovimento.COMPRA).save(produto);
        portfolioActor.limpaMapa();
    }

    @PostMapping(value = "produtos/renda-variavel")
    public void criaProdutoRV(@RequestBody ProdutoRVInfoVO produto) {
        cadastroProdutoResolver.resolve(produto.getTipoInvestimento(), TipoMovimento.COMPRA).save(produto);
        portfolioActor.limpaMapa();
    }



    @PostMapping(value = "produtos/arquivo-movimento")
    public ProdutoRVInfoVO salvarArquivoRV(@RequestBody MultipartFile arquivo) {
        fileUploadResolver.resolve(MOVIMENTOS).read(arquivo);
        portfolioActor.limpaMapa();
        return null;
    }

    @PostMapping(value = "produtos/limpar")
    public void limpar() {
        extratoActor.limpa();
        portfolioActor.limpaMapa();
    }

    @GetMapping(value = "dados-mercado/limpar")
    public void limparDadosMercado() {
        dadosMercadoActor.limpa();
    }

    @PostMapping(value = "produtos/movimento")
    public void criaMovimento(@RequestBody MovimentoInfoVO movimento) {
        cadastroProdutoResolver.resolve(null, movimento.getTipoMovimento()).save(movimento);
        portfolioActor.limpaMapa();
    }

    @PostMapping(value = "produtos/produtos-renda-fixa/download")
    public ResponseEntity exportaArquivoRF(@RequestBody List<ProdutoRFVO> produtos) {
        ExportedFile resource = fileExporterResolver.resolve(TypeFile.PRODUTOS_RF).export(produtos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .contentLength(resource.getFile().contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource.getFile());

    }

    @PostMapping(value = "produtos/produtos-renda-variavel/download")
    public ResponseEntity exportaArquivoRV(@RequestBody List<ProdutoRVVO> produtos) {
        ExportedFile resource = fileExporterResolver.resolve(PRODUTOS_RV).export(produtos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .contentLength(resource.getFile().contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource.getFile());

    }

    @RequestMapping("extrato")
    public List<ExtratoVO> extrato() {
        return extratoActor.getExtrato();
    }

    @PostMapping(value = "produtos/extrato/download")
    public ResponseEntity exportaExtrato(@RequestBody List<ExtratoVO> data) {
        ExportedFile resource = fileExporterResolver.resolve(MOVIMENTOS).export(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .contentLength(resource.getFile().contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource.getFile());

    }

    @GetMapping(value = "dados-mercado/atualiza")
    public void atualizaDadosMercado() {
        dadosMercadoActor.atualizaDadosMercado();
        portfolioActor.limpaMapa();
    }

    @GetMapping(value = "dados-mercado/busca")
    public DadosMercadoVO mostraDadosMercado() {
        return dadosMercadoActor.busca();
    }

}
