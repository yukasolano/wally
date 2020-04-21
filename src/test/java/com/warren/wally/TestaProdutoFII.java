package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.investimento.Investimento;
import com.warren.wally.model.investimento.InvestimentoResolver;
import com.warren.wally.model.investimento.ProdutoRVActor;
import com.warren.wally.model.investimento.ProdutoRVVO;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.MovimentacaoRepository;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDate;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

public class TestaProdutoFII extends WallyTestCase {

    @Autowired
    private ProdutoRVActor actor;

    @Resource
    private MovimentacaoRepository movimentacaoRepository;

    @Resource
    private CalculadoraResolver calculadoraResolver;

    @Resource
    private InvestimentoResolver investimentoResolver;

    @Test
    public void test() {

        LocalDate hoje = dateOf("20/08/2018");

        //cadastra produto
        ProdutoEntity entity = new ProdutoEntity();
        entity.setCodigo("VRTA11");
        entity.setTipoInvestimento(TipoInvestimento.FII);

        movimentacaoRepository.save(createMovimentacao(TipoMovimento.COMPRA,
                dateOf("04/06/2018"), "VRTA11", 2, 105.0));

        movimentacaoRepository.save(createMovimentacao(TipoMovimento.COMPRA,
                dateOf("27/07/2018"), "VRTA11", 2, 106.3));

        movimentacaoRepository.save(createMovimentacao(TipoMovimento.DIVIDENDO,
                dateOf("15/07/2018"), "VRTA11", 2, 0.6));

        movimentacaoRepository.save(createMovimentacao(TipoMovimento.DIVIDENDO,
                dateOf("15/08/2018"), "VRTA11", 4, 0.65));

        movimentacaoRepository.save(createMovimentacao(TipoMovimento.DIVIDENDO,
                dateOf("15/09/2018"), "VRTA11", 4, 0.7));


        Investimento invest = investimentoResolver.resolve(entity.getTipoInvestimento());
        ProdutoRVVO produto = (ProdutoRVVO) invest.calc(hoje, entity);


        assertEquals(105.65, produto.getPrecoMedio(), 0.01);
        assertEquals(4, produto.getQuantidade(), 0.01);
        assertEquals(0.075, produto.getRentabilidadeDividendo(), 0.01);
    }

    private MovimentacaoEntity createMovimentacao(TipoMovimento tipoMovimento,
                                                  LocalDate data,
                                                  String codigo,
                                                  Integer quantidade,
                                                  Double valor) {
        MovimentacaoEntity entity = new MovimentacaoEntity();

        entity.setTipoMovimento(tipoMovimento);
        entity.setCodigo(codigo);
        entity.setData(data);
        entity.setQuantidade(quantidade);
        entity.setValorUnitario(valor);

        return entity;
    }

}
