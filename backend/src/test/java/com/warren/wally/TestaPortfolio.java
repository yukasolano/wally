package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;
import com.warren.wally.portfolio.MultiPortfolio;
import com.warren.wally.portfolio.PortfolioActor;
import com.warren.wally.portfolio.PortfolioVO;
import com.warren.wally.portfolio.VariacaoVO;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import com.warren.wally.model.investimento.repository.MovimentacaoRepository;
import com.warren.wally.model.investimento.repository.ProdutoEntity;
import com.warren.wally.model.investimento.repository.ProdutoRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.time.LocalDate;

import static com.warren.wally.model.calculadora.TipoRentabilidade.CDI;
import static com.warren.wally.model.calculadora.TipoRentabilidade.IPCA;
import static com.warren.wally.model.calculadora.TipoRentabilidade.PRE;
import static com.warren.wally.model.investimento.TipoInvestimento.CDB;
import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;

public class TestaPortfolio extends WallyTestCase {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PortfolioActor portfolioActor;

    @Autowired
    private MultiPortfolio multiPortfolio;

    private LocalDate dataRef;

    @Resource
    private MovimentacaoRepository movimentacaoRepository;

    @Before
    public void inicializa() {
        dataRef = dateOf("10/12/2018");


        createProduto("MAXIMA-CDB-PRE", dateOf("31/05/2021"),
                0.1221, CDB, PRE, 10000d, dateOf("24/06/2016"));

        createProduto("MAXIMA-CDB-CDI", dateOf("31/05/2021"),
                1.22, CDB, CDI, 10000d, dateOf("24/06/2016"));

        createProduto("MAXIMA-CDB-IPCA", dateOf("15/12/2024"),
                0.07, CDB, IPCA, 1000d, dateOf("23/10/2018"));

    }


    @After
    public void clear() {
        produtoRepository.deleteAll();
    }

    @Test
    public void testAccrual() {
        PortfolioVO portfolio = portfolioActor.run(dataRef);
        assertEquals(26378.74, portfolio.getAccrual(), 0.01);
    }

    @Test
    public void testMultiportfolio() {
        PortfolioVO portfolio = portfolioActor.run(dataRef);
        VariacaoVO variacao = multiPortfolio.calculaVariacoes(portfolio);
        assertEquals(3132.92, variacao.getAnualAbsoluto(), 0.01);
        assertEquals(174.39, variacao.getMensalAbsoluto(), 0.01);
    }

    private void createProduto(String codigo,
                               LocalDate vencimento,
                               Double taxa,
                               TipoInvestimento tipoInvestimento,
                               TipoRentabilidade tipoRentabilidade,
                               Double valor,
                               LocalDate dataAplicacao) {
        //cadastra produto
        ProdutoEntity entity = new ProdutoEntity();
        entity.setCodigo(codigo);
        entity.setVencimento(vencimento);
        entity.setTaxa(taxa);
        entity.setTipoInvestimento(tipoInvestimento);
        entity.setTipoRentabilidade(tipoRentabilidade);
        produtoRepository.save(entity);

        //adiciona movimentacao de compra
        MovimentacaoEntity mov = new MovimentacaoEntity();
        mov.setTipoMovimento(TipoMovimento.COMPRA);
        mov.setCodigo(codigo);
        mov.setData(dataAplicacao);
        mov.setQuantidade(1);
        mov.setValorUnitario(valor);
        movimentacaoRepository.save(mov);
    }

}