package com.warren.wally.controller;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DadosMercadoVO {

    private List<SerieVO> dados = new ArrayList<>();

    public void addSerie(SerieVO serie) {
        dados.add(serie);
    }

    public void addSeries(List<SerieVO> series) {
        dados.addAll(series);
    }
}
