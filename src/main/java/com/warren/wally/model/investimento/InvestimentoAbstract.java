package com.warren.wally.model.investimento;

import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.utils.BussinessDaysCalendar;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

public abstract class InvestimentoAbstract implements Investimento {


    @Resource
    protected MovimentacaoRepository movimentacaoRepository;

    @Resource
    private BussinessDaysCalendar bc;

    protected boolean validaData(LocalDate dataRef,
                                 LocalDate dtAplicacao,
                                 LocalDate dtVencimento) {
        if (dtAplicacao.isAfter(dataRef)) {
            throw new RuntimeException(String.format("Produto não aplicado ainda. Data de aplicação: %s - Data: %s",
                    dtAplicacao, dataRef));
        }
        if (dtVencimento.isBefore(dataRef)) {
            throw new RuntimeException(String.format("Produto vencido. Data de vencimento: %s - Data: %s",
                    dtVencimento, dataRef));
        }
        return true;
    }


    protected ProdutoRFVO convertVO(ProdutoEntity entity,
                                    LocalDate dataRef) {
        List<MovimentacaoEntity> movimentacoes = movimentacaoRepository.findByCodigoAndDataLessThanOrderByData(entity.getCodigo(), dataRef);

        ProdutoRFVO vo = new ProdutoRFVO();
        for (MovimentacaoEntity mov : movimentacoes) {

            if (mov.getTipoMovimento().equals(TipoMovimento.COMPRA)) {

                validaData(dataRef, mov.getData(), entity.getVencimento());

                vo.setCorretora(mov.getCorretora());
                vo.setDtAplicacao(mov.getData());
                vo.setDtVencimento(entity.getVencimento());
                vo.setCodigo(entity.getCodigo());
                vo.setInstituicao(entity.getInstituicao());
                vo.setTaxa(entity.getTaxa());
                vo.setValorAplicado(mov.getQuantidade() * mov.getValorUnitario());
                vo.setTipoInvestimento(entity.getTipoInvestimento());
                vo.setTipoRentabilidade(entity.getTipoRentabilidade());

                vo.setDataReferencia(dataRef);
                vo.setDu(bc.getDu(vo.getDtAplicacao(), dataRef));
            }
        }

        return vo;
    }

    protected double getRentabilidadeLiquida(ProdutoRFVO vo) {
        return vo.getValorPresente() / vo.getValorAplicado() - 1;
    }

    protected double getTaxaAnualLiquida(ProdutoRFVO vo) {
        if (vo.getDu() == 0) {
            return 0;
        }
        try {
            return Math.pow(vo.getRentabilidadeLiquida() + 1, 252.0 / vo.getDu()) - 1;
        } catch (Exception exp) {
            return 0.0;
        }
    }

    protected double getTaxaMensalLiquida(ProdutoRFVO vo) {
        if (vo.getDu() == 0) {
            return 0;
        }
        try {
            return Math.pow(vo.getRentabilidadeLiquida() + 1, 21.0 / vo.getDu()) - 1;
        } catch (Exception exp) {
            return 0.0;
        }
    }
}
