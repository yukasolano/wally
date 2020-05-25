package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.dadosmercado.DMequitiesActor;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.warren.wally.model.investimento.TipoMovimento.ATUALIZACAO;
import static com.warren.wally.model.investimento.TipoMovimento.COMPRA;
import static com.warren.wally.model.investimento.TipoMovimento.DIVIDENDO;
import static com.warren.wally.model.investimento.TipoMovimento.JCP;
import static com.warren.wally.model.investimento.TipoMovimento.VENDA;

@Component
public class InvestimentoACAO extends InvestimentoAbstract {

    @Resource
    private CalculadoraResolver calculadoraResolver;


    @Autowired
    private DMequitiesActor dm;


    @Override
    public TipoInvestimento getTipoInvestimento() {
        return TipoInvestimento.ACAO;
    }

    @Override
    public ProdutoVO calc(LocalDate dataRef,
                          ProdutoEntity entity) {

        List<MovimentacaoEntity> movimentacoes = movimentacaoRepository
                .findByCodigoAndDataLessThanOrderByData(entity.getCodigo(), dataRef);

        ProdutoRVVO vo = new ProdutoRVVO(entity.getCodigo());
        vo.setTipoInvestimento(entity.getTipoInvestimento());
        vo.setTipoRentabilidade(TipoRentabilidade.ACAO);
        vo.setDataReferencia(dataRef);
        vo.setInstituicao(entity.getInstituicao());
        for (MovimentacaoEntity mov : movimentacoes) {

            if (mov.getTipoMovimento().equals(COMPRA)) {
                vo.setPrecoTotal(vo.getPrecoTotal() + mov.getValorUnitario() * mov.getQuantidade());
                vo.setQuantidade(vo.getQuantidade() + mov.getQuantidade());
                vo.setPrecoMedio(vo.getPrecoTotal() / vo.getQuantidade());
            }

            if (mov.getTipoMovimento().equals(VENDA)) {
                vo.setQuantidade(vo.getQuantidade() - mov.getQuantidade());
                if (vo.getQuantidade() == 0) {
                    vo.setPrecoMedio(0d);
                }
                vo.setPrecoTotal(vo.getQuantidade() * vo.getPrecoMedio());
            }

            if (mov.getTipoMovimento().equals(DIVIDENDO) || mov.getTipoMovimento().equals(JCP)
                    || mov.getTipoMovimento().equals(ATUALIZACAO)) {
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
        vo.setValorPresente(
                vo.getCotacao() == 0 ? vo.getPrecoTotal() : vo.getQuantidade() * vo.getCotacao());
        vo.setResultado(vo.getValorPresente() - vo.getPrecoTotal());
        vo.setRentabilidadeDividendo(getRentabilidade(vo));
        return vo;
    }

    private Double getRentabilidade(ProdutoRVVO produto) {

        if (produto.getQuantidade() == 0) {
            return 0d;
        }

        LocalDate anoAnterior = produto.getDataReferencia().minusYears(1);
        List<DividendoVO> dividendos = produto.getDividendos().stream().filter(it -> it.getData().isAfter(anoAnterior)).collect(Collectors.toList());
        double somaDividendos = dividendos.stream().mapToDouble(DividendoVO::getValorUnitario).sum();
        return somaDividendos / produto.getPrecoMedio();
    }

    public List<MovimentacaoEntity> getExtrato(LocalDate dataPosicao) {
        return movimentacaoRepository.findByDataBetweenOrderByData(dataPosicao.minusYears(1), dataPosicao);
    }

}
