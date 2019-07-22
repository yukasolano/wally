package com.warren.wally.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

public class DataMarketEquities {

	//TODO salvar no banco de dados, buscar sempre do yahoo é muito lento
	public double get(String ticker, LocalDate dataRef) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(dataRef.getYear(), dataRef.getMonthValue() - 1, dataRef.getDayOfMonth());
		Calendar calendar2 = Calendar.getInstance();
		calendar2.clear();
		calendar2.set(dataRef.getYear(), dataRef.getMonthValue() - 1, dataRef.getDayOfMonth());
		calendar2.add(Calendar.MONTH, 1);
		try {
			Stock stock = YahooFinance.get(ticker + ".SA");
			List<HistoricalQuote> googleHistQuotes = stock.getHistory(calendar, calendar2, Interval.DAILY);
			for (HistoricalQuote quote : googleHistQuotes) {
				LocalDate data = LocalDateTime.ofInstant(quote.getDate().toInstant(), ZoneId.systemDefault())
						.toLocalDate();
				if (data.equals(dataRef)) {
					return quote.getClose().doubleValue();
				}
			}
			/*
			 * List<DataValor> historico = googleHistQuotes.stream() .map(quote -> new
			 * DataValor(LocalDateTime.ofInstant( quote.getDate().toInstant(),
			 * ZoneId.systemDefault()).toLocalDate().toString(),
			 * quote.getClose().doubleValue())) .collect(Collectors.toList());
			 */
			// Optional<DataValor> valor = historico.stream().filter(hist ->
			// hist.getData().equals(dataRef)).findFirst();
			// if(valor.isPresent()) {
			// return valor.get().getValor();
			// }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0;
	}
}
