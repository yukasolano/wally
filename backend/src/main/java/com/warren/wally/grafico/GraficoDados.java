package com.warren.wally.grafico;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GraficoDados {

    private List<String> legendas = new ArrayList<>();
    private List<Double> valores = new ArrayList<>();

    public void addLegenda(String legenda) {
        legendas.add(legenda);
    }

    public void addValor(Double valor) {
        valores.add(valor);
    }

}
