package com.warren.wally.portfolio;

import com.warren.wally.grafico.GraficoMultiDados;

import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class GraficoSeries {
    private Map<String, Map<String, Double>> series = new HashMap<>();
    private Set<String> legendas = new TreeSet<>();

    public void addDado(String nome,
                        String label,
                        Double dado) {

        Map<String, Double> mapDados = series.computeIfAbsent(nome, it -> new TreeMap<>());
        mapDados.put(label, mapDados.getOrDefault(label, 0.0) + dado);
        legendas.add(label);
    }

    public GraficoMultiDados transforma() {

        double[][] dados = new double[series.size()][legendas.size()];
        String[] nomes = new String[series.size()];
        String[] labels = new String[legendas.size()];


        int l = 0;
        for (String label : legendas) {
            labels[l] = label;
            l++;
        }

        int s = 0;
        for( Map.Entry<String, Map<String, Double>> entry : series.entrySet()) {
            nomes[s] = entry.getKey();
            int a = 0;

            for (String label : labels) {
                dados[s][a] = entry.getValue().getOrDefault(label, 0d);
                a++;
            }
            s++;
        }

        return new GraficoMultiDados(labels, nomes, dados);

    }
}
