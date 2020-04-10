package com.warren.wally.controller;

import com.warren.wally.file.ExportedFile;
import com.warren.wally.file.FileExporterResolver;
import com.warren.wally.file.FileUploadResolver;
import com.warren.wally.file.TypeFile;
import com.warren.wally.grafico.GraficoTransformador;
import com.warren.wally.grafico.GraficosVO;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.dadosmercado.DadosMercadoActor;
import com.warren.wally.model.investimento.MovimentoInfoVO;
import com.warren.wally.model.investimento.ProdutoRFVO;
import com.warren.wally.model.investimento.ProdutoRVInfoVO;
import com.warren.wally.model.investimento.ProdutoRVVO;
import com.warren.wally.model.investimento.ProdutosVO;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.portfolio.MultiPortfolio;
import com.warren.wally.portfolio.PortfolioActor;
import com.warren.wally.portfolio.PortfolioVO;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;
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

import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;

import static com.warren.wally.file.TypeFile.MOVIMENTOS;
import static com.warren.wally.file.TypeFile.PRODUTOS_RV;

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

        String codigo = String.format("%s-%s-%s-%.4f-%s", produto.getInstituicao(), produto.getTipoInvestimento(),
                produto.getTipoRentabilidade(), produto.getTaxa(), produto.getDtVencimento());
        //cadastra produto
        ProdutoEntity entity = new ProdutoEntity();

        entity.setCodigo(codigo);
        entity.setInstituicao(produto.getInstituicao());
        entity.setVencimento(produto.getDtVencimento());
        entity.setTaxa(produto.getTaxa());
        entity.setTipoInvestimento(produto.getTipoInvestimento());
        entity.setTipoRentabilidade(produto.getTipoRentabilidade());
        produtoRepository.save(entity);

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo(codigo);
        mov.setData(produto.getDtAplicacao());
        mov.setQuantidade(1);
        mov.setValorUnitario(produto.getValorAplicado());
        movimentacaoRepository.save(mov);
        portfolioActor.limpaMapa();
    }


    @PostMapping(value = "produtos/arquivo-produto")
    public ProdutoRFInfoVO salvarArquivo(@RequestBody MultipartFile arquivo) {
        fileUploadResolver.resolve(TypeFile.INVESTIMENTOS).read(arquivo);
        portfolioActor.limpaMapa();
        return null;
    }

    @PostMapping(value = "produtos/renda-variavel")
    public void criaProdutoRV(@RequestBody ProdutoRVInfoVO produto) {

        //cadastra produto
        ProdutoEntity entity = new ProdutoEntity();

        entity.setCodigo(produto.getCodigo());
        entity.setTipoInvestimento(produto.getTipoInvestimento());
        entity.setTipoRentabilidade(getTipoRentabilidade(produto.getTipoInvestimento()));
        entity.setInstituicao("Renda variável");
        produtoRepository.save(entity);

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo(produto.getCodigo());
        mov.setData(produto.getData());
        mov.setQuantidade(produto.getQuantidade());
        mov.setValorUnitario(produto.getValorUnitario());
        movimentacaoRepository.save(mov);
        portfolioActor.limpaMapa();
    }

    private TipoRentabilidade getTipoRentabilidade(TipoInvestimento tipoInvestimento) {
        for (TipoRentabilidade tipoRentabilidade : TipoRentabilidade.values()) {
            if (tipoRentabilidade.toString().equals(tipoInvestimento.toString())) {
                return tipoRentabilidade;
            }
        }
        return null;
    }

    @PostMapping(value = "produtos/arquivo-movimento")
    public ProdutoRVInfoVO salvarArquivoRV(@RequestBody MultipartFile arquivo) {
        fileUploadResolver.resolve(MOVIMENTOS).read(arquivo);
        portfolioActor.limpaMapa();
        return null;
    }

    @PostMapping(value = "produtos/limpar")
    public void limpar() {
        produtoRepository.deleteAll();
        movimentacaoRepository.deleteAll();
        portfolioActor.limpaMapa();
    }

    @PostMapping(value = "produtos/movimento")
    public void criaMovimento(@RequestBody MovimentoInfoVO movimento) {

        List<ProdutoEntity> produtos = produtoRepository.findByCodigo(movimento.getCodigo());
        if (produtos.isEmpty()) {
            System.out.println("Produto não cadastrado " + movimento.getCodigo());
        } else {
            MovimentacaoEntity entity = new MovimentacaoEntity();
            entity.setTipoMovimento(movimento.getTipoMovimento());
            entity.setValorUnitario(movimento.getValorUnitario());
            entity.setQuantidade(movimento.getQunatidade());
            entity.setData(movimento.getData());
            entity.setCodigo(movimento.getCodigo());
            movimentacaoRepository.save(entity);
        }
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

    @PostMapping(value = "produtos/extrato/download")
    public ResponseEntity exportaExtrato(@RequestBody List<MovimentacaoEntity> data) {
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
