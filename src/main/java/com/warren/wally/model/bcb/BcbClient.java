package com.warren.wally.model.bcb;

import com.warren.wally.utils.DataValor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BcbClient {

    @Autowired
    private BcbConnector bcbConnector;

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public List<DataValor> getCDI(LocalDate dataInicial, LocalDate dataFinal) {
        return bcbConnector.cdiSearch("4389", "json", dataInicial.format(dtf), dataFinal.format(dtf)).stream()
                .map(it -> new DataValor(it.getData(), it.getValor()))
                .collect(Collectors.toList());
    }

    public List<DataValor> getIPCA(LocalDate dataInicial, LocalDate dataFinal) {
        return bcbConnector.cdiSearch("433", "json", dataInicial.format(dtf), dataFinal.format(dtf)).stream()
                .map(it -> new DataValor(it.getData(), it.getValor()))
                .collect(Collectors.toList());
    }
}
