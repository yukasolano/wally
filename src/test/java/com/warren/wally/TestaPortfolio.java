package com.warren.wally;

import com.warren.wally.model.PortfolioActor;
import com.warren.wally.model.PortfolioVO;
import com.warren.wally.model.Produto;
import com.warren.wally.model.ProdutoFactory;
import com.warren.wally.repository.ProdutoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WallyApplication.class})
@TestPropertySource(locations = "/application.properties")
public class TestaPortfolio {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private PortfolioActor portfolioActor;

    @Test
    public void test() {

        List<Produto> produtos = new ArrayList<>();
        repository.findAll().forEach(entity -> {
            Produto produto = ProdutoFactory.getProduto(entity);
            try {
                if (produto != null) {
                    produtos.add(produto);
                }

            } catch (Exception exp) {
                System.out.println(exp);
            }
        });
        LocalDate hoje = LocalDate.of(2018, 12, 10);
        PortfolioVO portfolio = portfolioActor.run(hoje);
        assertEquals(60123.097, portfolio.getAccrual(), 0.01);
    }

}
