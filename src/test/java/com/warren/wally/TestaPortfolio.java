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

import com.warren.wally.model.IProduto;
import com.warren.wally.model.Portfolio;
import com.warren.wally.model.ProdutoCDB;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WallyApplication.class})
public class TestaPortfolio {

	
	@Autowired
	public Portfolio portfolio;
	
	@Test
	public void test() {
		LocalDate hoje = LocalDate.of(2018, 12, 10);
		assertEquals(60123.097, portfolio.getAccrual(hoje), 0.01);
	}

}
