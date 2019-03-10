package com.warren.wally;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.warren.wally.model.Portfolio;
import com.warren.wally.model.Produto;
import com.warren.wally.model.ProdutoFactory;
import com.warren.wally.repository.ProdutoRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WallyApplication.class})
public class TestaPortfolio {

	
	@Autowired
	private ProdutoRepository repository;
	
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
		Portfolio portfolio = new Portfolio(produtos, hoje);
		assertEquals(60123.097, portfolio.getAccrual(), 0.01);
	}

}
