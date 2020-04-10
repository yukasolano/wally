package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.dadosmercado.DataMarketEquities;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InvestimentoACAO extends InvestimentoAbstract {

    @Resource
    private CalculadoraResolver calculadoraResolver;


    @Autowired
    private DataMarketEquities dm;


    @Override
    public TipoInvestimento getTipoInvestimento() {
        return TipoInvestimento.ACAO;
    }

    @Override
    public ProdutoVO calc(LocalDate dataRef, ProdutoEntity entity) {

        List<MovimentacaoEntity> movimentacoes = movimentacaoRepository
                .findByCodigoAndDataLessThanOrderByData(entity.getCodigo(), dataRef);

        ProdutoRVVO vo = new ProdutoRVVO(entity.getCodigo());
        vo.setTipoInvestimento(entity.getTipoInvestimento());
        vo.setTipoRentabilidade(TipoRentabilidade.ACAO);
        vo.setDataReferencia(dataRef);
        for ( MovimentacaoEntity mov : movimentacoes ) {

            if(mov.getTipoMovimento().equals(TipoMovimento.COMPRA)) {
                vo.setPrecoTotal(vo.getPrecoTotal() + mov.getValorUnitario() * mov.getQuantidade());
                vo.setQuantidade(vo.getQuantidade() + mov.getQuantidade());
            }

            if(mov.getTipoMovimento().equals(TipoMovimento.DIVIDENDO)) {
                DividendoVO dividendoVO = new DividendoVO();
                dividendoVO.setCodigo(mov.getCodigo());
                dividendoVO.setData(mov.getData());
                dividendoVO.setQuantidade(mov.getQuantidade());
                dividendoVO.setTipo(mov.getTipoMovimento());
                dividendoVO.setValorUnitario(mov.getValorUnitario());
                vo.addDividendo(dividendoVO);
            }
        }


        vo.setCotacao(dm.get(vo.getCodigo(), dataRef));
        vo.setPrecoMedio(vo.getPrecoTotal() / vo.getQuantidade());
        vo.setValorPresente(
                vo.getCotacao() == 0 ? vo.getPrecoTotal() : vo.getQuantidade() * vo.getCotacao());
        vo.setResultado(vo.getValorPresente() - vo.getPrecoTotal());
        vo.setRentabilidadeDividendo(getRentabilidade(vo));
        return vo;
    }

    private Double getRentabilidade(ProdutoRVVO produto) {
        LocalDate anoAnterior = produto.getDataReferencia().minusYears(1);
        List<DividendoVO> dividendos = produto.getDividendos().stream().filter(it -> it.getData().isAfter(anoAnterior)).collect(Collectors.toList());
        double somaDividendos = dividendos.stream().mapToDouble(DividendoVO::getValorUnitario).sum();
        return somaDividendos / produto.getPrecoMedio();
    }

    public List<MovimentacaoEntity> getExtrato(LocalDate dataPosicao) {
        return movimentacaoRepository.findByDataBetweenOrderByData(dataPosicao.minusYears(1), dataPosicao);
    }

}
