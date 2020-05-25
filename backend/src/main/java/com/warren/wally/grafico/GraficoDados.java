package com.warren.wally.grafico;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GraficoDados {

    private List<String> legendas;
    private List<Double> valores;

    public void addLegenda(String legenda) {
        if (legendas == null) {
            legendas = new ArrayList<>();
        }
        legendas.add(legenda);
    }

    public void addValor(Double valor) {
        if (valores == null) {
            valores = new ArrayList<>();
        }
        valores.add(valor);
    }

}
