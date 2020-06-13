package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.dadosmercado.DMequitiesActor;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.MovimentacaoRepository;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.warren.wally.model.investimento.TipoMovimento.VENDA;

public abstract class InvestimentoRVAbstract implements Investimento {

    @Autowired
    private DMequitiesActor dm;

    @Resource
    protected MovimentacaoRepository movimentacaoRepository;

    @Override
    public ProdutoVO calc(LocalDate dataRef,
                          ProdutoEntity entity) {

        List<MovimentacaoEntity> movimentacoes = movimentacaoRepository
                .findByCodigoOrderByData(entity.getCodigo());


        if(movimentacoes.isEmpty()) {
            throw new RuntimeException(String.format("Produto sem movimentações em %s", dataRef));
        }

        ProdutoRVVO vo = new ProdutoRVVO(entity.getCodigo());
        vo.setMovimentacoes(movimentacoes);
        vo.setTipoInvestimento(entity.getTipoInvestimento());
        vo.setDataReferencia(dataRef);
        vo.setTipoRentabilidade(getTipoRentabilidade());
        vo.setInstituicao(entity.getInstituicao());
        List<MovimentacaoEntity> movimentacoesFiltradas = movimentacoes.stream().filter(it -> it.getData().isBefore(dataRef) || it.getData().isEqual(dataRef)).collect(Collectors.toList());
        if(movimentacoesFiltradas.isEmpty()) {
            throw new RuntimeException(String.format("Produto sem movimentações em %s", dataRef));
        }
        for (MovimentacaoEntity mov : movimentacoesFiltradas) {
            atualizaMov(vo, mov);
        }

        vo.setCotacao(dm.get(vo.getCodigo(), dataRef));
        vo.setValorPresente(
                vo.getCotacao() == 0 ? vo.getPrecoTotal() : vo.getQuantidade() * vo.getCotacao());
        vo.setResultado(vo.getValorPresente() - vo.getPrecoTotal());
        vo.setRentabilidadeDividendo(getRentabilidadeDividendo(vo));
        return vo;
    }

    @Override
    public ProdutoVO calcAcum(LocalDate dataRef,
                              ProdutoVO voAnterior) {

        ProdutoRVVO voRVAnterior = (ProdutoRVVO) voAnterior;
        ProdutoRVVO vo = new ProdutoRVVO(voRVAnterior.getCodigo());
        vo.setMovimentacoes(voRVAnterior.getMovimentacoes());
        vo.setTipoInvestimento(voRVAnterior.getTipoInvestimento());
        vo.setDataReferencia(dataRef);
        vo.setTipoRentabilidade(voRVAnterior.getTipoRentabilidade());
        vo.setInstituicao(voRVAnterior.getInstituicao());
        vo.setQuantidade(voRVAnterior.getQuantidade());
        vo.setPrecoMedio(voRVAnterior.getPrecoMedio());
        vo.setPrecoTotal(voRVAnterior.getPrecoTotal());
        vo.setUltimaCompra(voRVAnterior.getUltimaCompra());
        vo.setDividendos(voRVAnterior.getDividendos());
        List<MovimentacaoEntity> movimentacoesFiltradas = vo.getMovimentacoes().stream().filter(it -> it.getData().isAfter(voAnterior.getDataReferencia()) &&(it.getData().isBefore(dataRef) || it.getData().isEqual(dataRef))).collect(Collectors.toList());
        if(movimentacoesFiltradas.isEmpty()) {
            throw new RuntimeException(String.format("Produto sem movimentações em %s", dataRef));
        }
        for (MovimentacaoEntity mov : movimentacoesFiltradas) {
            atualizaMov(vo, mov);
        }


        vo.setCotacao(dm.get(vo.getCodigo(), dataRef));
        vo.setValorPresente(
                vo.getCotacao() == 0 ? vo.getPrecoTotal() : vo.getQuantidade() * vo.getCotacao());
        vo.setResultado(vo.getValorPresente() - vo.getPrecoTotal());
        vo.setRentabilidadeDividendo(getRentabilidadeDividendo(vo));
        return vo;
    }

    private void atualizaMov(ProdutoRVVO vo, MovimentacaoEntity mov) {
        if (mov.getTipoMovimento().equals(TipoMovimento.COMPRA)) {
            atualizaMovCompra(vo, mov);
        }

        if (mov.getTipoMovimento().equals(VENDA)) {
            atualizaMovVenda(vo, mov);
        }

        if (mov.getTipoMovimento().equals(TipoMovimento.DIVIDENDO)) {
            atualizaMovDiv(vo, mov);
        }
    }

    private void atualizaMovCompra(ProdutoRVVO vo, MovimentacaoEntity mov) {
        vo.setPrecoTotal(vo.getPrecoTotal() + mov.getValorUnitario() * mov.getQuantidade());
        vo.setQuantidade(vo.getQuantidade() + mov.getQuantidade());
        vo.setPrecoMedio(vo.getPrecoTotal() / vo.getQuantidade());
        vo.setUltimaCompra(mov.getData());
    }

    private void atualizaMovVenda(ProdutoRVVO vo, MovimentacaoEntity mov) {
        vo.setQuantidade(vo.getQuantidade() - mov.getQuantidade());
        if (vo.getQuantidade() == 0) {
            vo.setPrecoMedio(0d);
        }
        vo.setPrecoTotal(vo.getQuantidade() * vo.getPrecoMedio());
        vo.setUltimaCompra(mov.getData());
    }

    private void atualizaMovDiv(ProdutoRVVO vo, MovimentacaoEntity mov) {
        DividendoVO dividendoVO = new DividendoVO();
        dividendoVO.setCodigo(mov.getCodigo());
        dividendoVO.setData(mov.getData());
        dividendoVO.setQuantidade(mov.getQuantidade());
        dividendoVO.setTipo(mov.getTipoMovimento());
        dividendoVO.setValorUnitario(mov.getValorUnitario());
        vo.addDividendo(dividendoVO);
    }

    abstract double getRentabilidadeDividendo(ProdutoRVVO produto);
    abstract TipoRentabilidade getTipoRentabilidade();

}
