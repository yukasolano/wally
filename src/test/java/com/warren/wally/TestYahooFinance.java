package com.warren.wally;

import static org.junit.Assert.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;

import com.warren.wally.model.DataMarketEquities;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class TestYahooFinance {

	@Test
	public void test() {

		DataMarketEquities dm = new DataMarketEquities();
		double valor = dm.get("VRTA11", LocalDate.of(2019, 01, 02));
		System.out.println(valor);
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
		}

	}

}
