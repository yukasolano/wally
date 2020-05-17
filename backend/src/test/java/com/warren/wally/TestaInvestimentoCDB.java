package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.calculadora.CalculadoraResolver;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.Investimento;
import com.warren.wally.model.investimento.InvestimentoResolver;
import com.warren.wally.model.investimento.ProdutoRFVO;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.MovimentacaoRepository;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDate;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

public class TestaInvestimentoCDB extends WallyTestCase {

    @Resource
    private CalculadoraResolver calculadoraResolver;

    @Resource
    private InvestimentoResolver investimentoResolver;

    @Resource
    private MovimentacaoRepository movimentacaoRepository;

    @Test
    public void testCalculoPre() {

        LocalDate hoje = dateOf("10/12/2018");

        //cadastra produto
        ProdutoEntity entity = new ProdutoEntity();
        entity.setCodigo("MAXIMA-CDB-PRE-12-2021-05-31");
        entity.setVencimento(dateOf("31/05/2021"));
        entity.setTaxa(0.1221);
        entity.setTipoInvestimento(TipoInvestimento.CDB);
        entity.setTipoRentabilidade(TipoRentabilidade.PRE);

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo("MAXIMA-CDB-PRE-12-2021-05-31");
        mov.setData(dateOf("24/06/2016"));
        mov.setQuantidade(1);
        mov.setValorUnitario(10000);
        movimentacaoRepository.save(mov);

        Investimento invest = investimentoResolver.resolve(entity.getTipoInvestimento());
        ProdutoRFVO produto = (ProdutoRFVO) invest.calc(hoje, entity);
        assertEquals(12764.67, produto.getValorPresente(), 0.01);
        assertEquals(27.65, produto.getRentabilidadeLiquida() * 100, 0.01);
        assertEquals(10.50, produto.getTaxaAnualLiquida() * 100, 0.01);
        assertEquals(0.84, produto.getTaxaMensalLiquida() * 100, 0.01);
    }

    @Test
    public void testCalculoCDI() {

        LocalDate hoje = dateOf("10/12/2018");

        ProdutoEntity entity = new ProdutoEntity();
        entity.setCodigo("MAXIMA-CDB-CDI-122-2021-05-31");
        entity.setVencimento(dateOf("31/05/2021"));
        entity.setTaxa(1.22);
        entity.setTipoInvestimento(TipoInvestimento.CDB);
        entity.setTipoRentabilidade(TipoRentabilidade.CDI);

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo("MAXIMA-CDB-CDI-122-2021-05-31");
        mov.setData(dateOf("24/06/2016"));
        mov.setQuantidade(1);
        mov.setValorUnitario(10000);
        movimentacaoRepository.save(mov);

        Investimento invest = investimentoResolver.resolve(entity.getTipoInvestimento());
        ProdutoRFVO produto = (ProdutoRFVO) invest.calc(hoje, entity);
        assertEquals(12606.04, produto.getValorPresente(), 0.01);
        assertEquals(26.06, produto.getRentabilidadeLiquida() * 100, 0.01);
        assertEquals(9.94, produto.getTaxaAnualLiquida() * 100, 0.01);
        assertEquals(0.79, produto.getTaxaMensalLiquida() * 100, 0.01);
    }

    @Test
    public void testCalculoIPCA() {
        LocalDate hoje = dateOf("10/12/2018");

        ProdutoEntity entity = new ProdutoEntity();
        entity.setCodigo("MAXIMA-CDB-IPCA-7-2024-12-15");
        entity.setVencimento(dateOf("15/12/2024"));
        entity.setTaxa(0.07);
        entity.setTipoInvestimento(TipoInvestimento.CDB);
        entity.setTipoRentabilidade(TipoRentabilidade.IPCA);

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo("MAXIMA-CDB-IPCA-7-2024-12-15");
        mov.setData(dateOf("23/10/2018"));
        mov.setQuantidade(1);
        mov.setValorUnitario(1000);
        movimentacaoRepository.save(mov);

        Investimento invest = investimentoResolver.resolve(entity.getTipoInvestimento());
        ProdutoRFVO produto = (ProdutoRFVO) invest.calc(hoje, entity);

        assertEquals(1008.03, produto.getValorPresente(), 0.01);
        assertEquals(0.80, produto.getRentabilidadeLiquida() * 100, 0.01);
        assertEquals(6.50, produto.getTaxaAnualLiquida() * 100, 0.01);
        assertEquals(0.53, produto.getTaxaMensalLiquida() * 100, 0.01);
    }

    @Test
    public void testCalculoPreLCI() {

        LocalDate hoje = dateOf("10/12/2018");

        ProdutoEntity entity = new ProdutoEntity();
        entity.setCodigo("MAXIMA-CLCI-PRE-12-2016-06-24");
        entity.setVencimento(dateOf("31/05/2021"));
        entity.setTaxa(0.1221);
        entity.setTipoInvestimento(TipoInvestimento.LCI);
        entity.setTipoRentabilidade(TipoRentabilidade.PRE);

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo("MAXIMA-CLCI-PRE-12-2016-06-24");
        mov.setData(dateOf("24/06/2016"));
        mov.setQuantidade(1);
        mov.setValorUnitario(10000);
        movimentacaoRepository.save(mov);

        Investimento invest = investimentoResolver.resolve(entity.getTipoInvestimento());
        ProdutoRFVO produto = (ProdutoRFVO) invest.calc(hoje, entity);
        assertEquals(13252.55, produto.getValorPresente(), 0.01);
        assertEquals(32.53, produto.getRentabilidadeLiquida() * 100, 0.01);
        assertEquals(12.21, produto.getTaxaAnualLiquida() * 100, 0.01);
        assertEquals(0.96, produto.getTaxaMensalLiquida() * 100, 0.01);
    }
}
