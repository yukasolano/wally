package com.warren.wally;

import com.warren.wally.db.WallyTestCase;
import com.warren.wally.model.PortfolioActor;
import com.warren.wally.model.PortfolioVO;
import com.warren.wally.repository.ProdutoRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static com.warren.wally.utils.DateUtils.dateOf;
import static org.junit.Assert.assertEquals;


public class TestaPortfolio extends WallyTestCase {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private PortfolioActor portfolioActor;

    @Test
    public void test() {

        // TODO: Precisamos recuperar os produtos aqui ou o PortfolioActor que é responsável por isso?
//        List<Produto> produtos = new ArrayList<>();
//        repository.findAll().forEach(entity -> {
//            Produto produto = ProdutoFactory.getProduto(entity);
//            try {
//                if (produto != null) {
//                    produtos.add(produto);
//                }
//
//            } catch (Exception exp) {
//                System.out.println(exp);
//            }
//        });
        LocalDate hoje = dateOf("10/12/2018");
        PortfolioVO portfolio = portfolioActor.run(hoje);
        assertEquals(60123.097, portfolio.getAccrual(), 0.01);
    }

}
