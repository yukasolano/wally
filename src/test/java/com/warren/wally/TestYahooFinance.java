package com.warren.wally;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.warren.wally.model.DataMarketEquities;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WallyApplication.class})
public class TestYahooFinance {

	@Resource
	private DataMarketEquities dm;
	@Test
	public void test() {

		double valor = dm.get("VISC11", LocalDate.of(2019, 01, 02));
		System.out.println(valor);
		/*
		Stock stock;
		try {
			stock = YahooFinance.get("VRTA11.SA", true);
			System.out.println("Lendooooo");
			BigDecimal price = stock.getQuote().getPrice();
			BigDecimal change = stock.getQuote().getChangeInPercent();
			BigDecimal peg = stock.getStats().getPeg();
			BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

			System.out.println(price);
			System.out.println(dividend);
			System.out.println("uhulll");
			stock.print();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}

}
