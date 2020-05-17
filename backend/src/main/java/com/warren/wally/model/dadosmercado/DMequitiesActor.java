package com.warren.wally.model.dadosmercado;

import com.warren.wally.controller.SerieVO;
import com.warren.wally.model.dadosmercado.repository.BolsaEntity;
import com.warren.wally.model.dadosmercado.repository.BolsaRepository;
import com.warren.wally.model.dadosmercado.yahoo.YahooClient;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import com.warren.wally.model.investimento.repository.ProdutoRepository;
import com.warren.wally.utils.BussinessDaysCalendar;
import com.warren.wally.utils.DataValor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DMequitiesActor {

    @Resource
    private BolsaRepository bolsaRepository;

    @Resource
    private BussinessDaysCalendar bc;

    @Resource
    private ProdutoRepository produtoRepository;

    @Resource
    private YahooClient yahoo;

    private LocalDate startDate = LocalDate.of(2015, 1, 1);

    public void limpa() {
        bolsaRepository.deleteAll();
    }

    public void atualiza() {
        getProdutos().forEach(it -> atualiza(it.getCodigo()));
    }

    public List<SerieVO> get() {
        return getProdutos().stream().map(it -> get(it.getCodigo())).collect(Collectors.toList());
    }

    public double get(String ticker,
                      LocalDate data) {
        List<BolsaEntity> cdiEntity = bolsaRepository.findByCodigoAndData(ticker, data);

        if (!cdiEntity.isEmpty()) {
            return cdiEntity.get(0).getValor();
        }

        cdiEntity = bolsaRepository.findByCodigoOrderByDataDesc(ticker);

        if (!cdiEntity.isEmpty()) {
            return cdiEntity.get(0).getValor();
        }
        System.out.println("Ticker não encontrado: " + ticker);
        return 0.0;
    }

    private void atualiza(String ticker) {
        //verifica ultima data no banco
        List<BolsaEntity> cdiEntity = bolsaRepository.findByCodigoOrderByDataDesc(ticker);
        LocalDate ultimaData = cdiEntity.isEmpty() ? startDate : cdiEntity.get(0).getData();

        //busca dados
        List<DataValor> valores = yahoo.getTicket(ticker, ultimaData.plusDays(1), LocalDate.now());

        //atualiza banco
        List<BolsaEntity> novosDados = valores.stream().map(it -> {
            BolsaEntity entity = new BolsaEntity();
            entity.setCodigo(ticker);
            entity.setData(it.getData());
            entity.setValor(it.getValor());
            return entity;
        }).collect(Collectors.toList());

        bolsaRepository.saveAll(novosDados);
    }

    private SerieVO get(String ticker) {
        List<BolsaEntity> cdiEntity = bolsaRepository.findByCodigoOrderByDataDesc(ticker);
        List<DataValor> cdi = cdiEntity.stream().map(it -> new DataValor(it.getData(), it.getValor())).collect(Collectors.toList());
        SerieVO vo = new SerieVO();
        vo.setNome(ticker);
        vo.setValores(cdi);
        return vo;
    }


    private List<ProdutoEntity> getProdutos() {
        List<ProdutoEntity> produtos = produtoRepository.findByTipoInvestimento(TipoInvestimento.FII);
        produtos.addAll(produtoRepository.findByTipoInvestimento(TipoInvestimento.ACAO));
        return produtos;
    }

}
