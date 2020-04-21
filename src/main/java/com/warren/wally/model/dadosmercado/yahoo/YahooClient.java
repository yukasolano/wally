package com.warren.wally.model.dadosmercado.yahoo;

import com.warren.wally.model.dadosmercado.repository.BolsaEntity;
import com.warren.wally.utils.DataValor;
import org.springframework.stereotype.Component;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class YahooClient {

    public List<DataValor> getTicket(String ticker,
                                      LocalDate startDate,
                                      LocalDate endDate) {
        try {
            List<DataValor> valores = new ArrayList<>();
            Stock stock = YahooFinance.get(ticker + ".SA");
            List<HistoricalQuote> histQuotes = stock.getHistory(toCalendar(startDate), toCalendar(endDate), Interval.DAILY);
            for (HistoricalQuote quote : histQuotes) {
                if (quote.getDate() != null && quote.getClose() != null) {
                    LocalDate data = LocalDateTime.ofInstant(quote.getDate().toInstant(), ZoneId.systemDefault())
                            .toLocalDate();
                    DataValor bolsa = new DataValor();
                    bolsa.setData(data);
                    bolsa.setValor(quote.getClose().doubleValue());
                    valores.add(bolsa);
                }
            }
            return valores;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    private Calendar toCalendar(LocalDate localdate) {
        Date date = Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

}
