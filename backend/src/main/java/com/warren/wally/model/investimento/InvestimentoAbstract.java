package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.Calculadora;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.MovimentacaoRepository;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import com.warren.wally.utils.BussinessDaysCalendar;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

public abstract class InvestimentoAbstract implements Investimento {

    @Resource
    private CalculadoraResolver calculadoraResolver;

    @Resource
    protected MovimentacaoRepository movimentacaoRepository;

    @Resource
    private BussinessDaysCalendar bc;

    @Override
    public ProdutoVO calc(LocalDate dataRef,
                          ProdutoEntity entity) {

        ProdutoRFVO vo = convertVO(entity, dataRef);

        Calculadora calc = calculadoraResolver.resolve(vo.getTipoRentabilidade());

        double VPBruto = calc.calculaVPBruto(vo.getValorAplicado(), vo.getTaxa(), vo.getDtAplicacao(),
                vo.getDataReferencia());
        vo.setValorPresente(getValorPresente(vo, VPBruto));
        vo.setRentabilidadeLiquida(getRentabilidadeLiquida(vo));
        vo.setTaxaAnualLiquida(getTaxaAnualLiquida(vo));
        vo.setTaxaMensalLiquida(getTaxaMensalLiquida(vo));

        return vo;
    }


    @Override
    public ProdutoVO calcAcum(LocalDate dataRef,
                              ProdutoVO voAnterior) {


        ProdutoRFVO vo = clone((ProdutoRFVO) voAnterior, dataRef);


        Calculadora calc = calculadoraResolver.resolve(vo.getTipoRentabilidade());

        double VPBruto = calc.calculaVPBruto(voAnterior.getValorPresente(), vo.getTaxa(), voAnterior.getDataReferencia(),
                vo.getDataReferencia());
        vo.setValorPresente(getValorPresente(vo, VPBruto));
        vo.setRentabilidadeLiquida(getRentabilidadeLiquida(vo));
        vo.setTaxaAnualLiquida(getTaxaAnualLiquida(vo));
        vo.setTaxaMensalLiquida(getTaxaMensalLiquida(vo));

        return vo;
    }

    abstract double getValorPresente(ProdutoRFVO vo, double VPBruto);

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

        if(movimentacoes.isEmpty()) {
            throw new RuntimeException(String.format("Produto sem movimentações em %s", dataRef));
        }

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

    protected ProdutoRFVO clone(ProdutoRFVO vo,
                                    LocalDate dataRef) {

        ProdutoRFVO newVO = new ProdutoRFVO();
        newVO.setCorretora(vo.getCorretora());
        newVO.setDtAplicacao(vo.getDtAplicacao());
        newVO.setDtVencimento(vo.getDtVencimento());
        newVO.setCodigo(vo.getCodigo());
        newVO.setInstituicao(vo.getInstituicao());
        newVO.setTaxa(vo.getTaxa());
        newVO.setValorAplicado(vo.getValorAplicado());
        newVO.setTipoInvestimento(vo.getTipoInvestimento());
        newVO.setTipoRentabilidade(vo.getTipoRentabilidade());

        newVO.setDataReferencia(dataRef);
        newVO.setDu(bc.getDu(vo.getDtAplicacao(), dataRef));

        return newVO;
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
