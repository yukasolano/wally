package com.warren.wally.controller;

import com.warren.wally.file.ExportedFile;
import com.warren.wally.file.FileExporterResolver;
import com.warren.wally.file.FileUploadResolver;
import com.warren.wally.file.TypeFile;
import com.warren.wally.model.cadastro.CadastroProdutoResolver;
import com.warren.wally.model.cadastro.ExtratoActor;
import com.warren.wally.model.cadastro.MovimentoInfoVO;
import com.warren.wally.model.cadastro.ProdutoRFInfoVO;
import com.warren.wally.model.cadastro.ProdutoRVInfoVO;
import com.warren.wally.model.investimento.ProdutoRFVO;
import com.warren.wally.model.investimento.ProdutoRVVO;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.portfolio.PortfolioActor;
import com.warren.wally.portfolio.PortfolioVO;
import com.warren.wally.utils.BussinessDaysCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.warren.wally.file.TypeFile.MOVIMENTOS;
import static com.warren.wally.file.TypeFile.PRODUTOS_RV;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private PortfolioActor portfolioActor;

    @Autowired
    private FileUploadResolver fileUploadResolver;

    @Resource
    private CadastroProdutoResolver cadastroProdutoResolver;

    @Resource
    private ExtratoActor extratoActor;

    @Resource
    private BussinessDaysCalendar bc;

    @Autowired
    private FileExporterResolver fileExporterResolver;


    @RequestMapping("/renda-fixa")
    public List<ProdutoRFVO> produtosRF(@PathParam("date") String date) {
        PortfolioVO portfolio = portfolioActor.run(bc.getPreviousWorkDay(LocalDate.parse(date)), null);
        return portfolio.getProdutos().stream().filter(it -> it instanceof ProdutoRFVO).map(it -> (ProdutoRFVO) it).collect(Collectors.toList());
    }

    @RequestMapping("/renda-variavel")
    public List<ProdutoRVVO> produtosRV(@PathParam("date") String date) {
        PortfolioVO portfolio = portfolioActor.run(bc.getPreviousWorkDay(LocalDate.parse(date)), null);
        return portfolio.getProdutos().stream().filter(it -> it instanceof ProdutoRVVO).map(it -> (ProdutoRVVO) it).collect(Collectors.toList());
    }

    @PostMapping(value = "/renda-fixa")
    public MessageOutDTO criaProdutoRF(@RequestBody ProdutoRFInfoVO produto) {
        cadastroProdutoResolver.resolve(produto.getTipoInvestimento(), TipoMovimento.COMPRA).save(produto);
        portfolioActor.limpaMapa();
        return MessageOutDTO.ok("Produto cadastrado com sucesso");
    }

    @PostMapping(value = "/renda-variavel")
    public MessageOutDTO criaProdutoRV(@RequestBody ProdutoRVInfoVO produto) {
        cadastroProdutoResolver.resolve(produto.getTipoInvestimento(), TipoMovimento.COMPRA).save(produto);
        portfolioActor.limpaMapa();
        return MessageOutDTO.ok("Produto cadastrado com sucesso");
    }


    @PostMapping(value = "/arquivo-movimento")
    public MessageOutDTO salvarArquivoRV(@RequestBody MultipartFile arquivo) {
        fileUploadResolver.resolve(MOVIMENTOS).read(arquivo);
        portfolioActor.limpaMapa();
        return MessageOutDTO.ok("Arquivo importado com sucesso");
    }

    @PostMapping(value = "/limpar")
    public MessageOutDTO limpar() {
        extratoActor.limpa();
        portfolioActor.limpaMapa();
        return MessageOutDTO.ok("Produtos removidos com sucesso");
    }


    @PostMapping(value = "/movimento")
    public MessageOutDTO criaMovimento(@RequestBody MovimentoInfoVO movimento) {
        cadastroProdutoResolver.resolve(null, movimento.getTipoMovimento()).save(movimento);
        portfolioActor.limpaMapa();
        return MessageOutDTO.ok("Movimento cadastrado com sucesso");
    }

    @PostMapping(value = "/produtos-renda-fixa/download")
    public ResponseEntity exportaArquivoRF(@RequestBody List<ProdutoRFVO> produtos) {
        ExportedFile resource = fileExporterResolver.resolve(TypeFile.PRODUTOS_RF).export(produtos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .contentLength(resource.getFile().contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource.getFile());

    }

    @PostMapping(value = "/produtos-renda-variavel/download")
    public ResponseEntity exportaArquivoRV(@RequestBody List<ProdutoRVVO> produtos) {
        ExportedFile resource = fileExporterResolver.resolve(PRODUTOS_RV).export(produtos);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .contentLength(resource.getFile().contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource.getFile());

    }

    @RequestMapping("/extrato")
    public List<ExtratoVO> extrato() {
        return extratoActor.getExtrato();
    }

    @PostMapping(value = "/extrato/download")
    public ResponseEntity exportaExtrato(@RequestBody List<ExtratoVO> data) {
        ExportedFile resource = fileExporterResolver.resolve(MOVIMENTOS).export(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resource.getFilename())
                .contentLength(resource.getFile().contentLength())
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(resource.getFile());

    }


}
