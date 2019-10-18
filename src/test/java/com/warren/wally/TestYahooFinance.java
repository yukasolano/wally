package com.warren.wally;

import com.warren.wally.model.DataMarketEquities;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.time.LocalDate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {WallyApplication.class})
@TestPropertySource(locations = "/application.properties")
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
