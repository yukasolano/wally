package com.warren.wally.portfolio;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ProporcaoVO {

    private String nome;
    private Double valor;
    private Double porcentagem;
    private List<ProporcaoVO> categorias ;

    ProporcaoVO(String nome, Double valor, Double total) {
        this.nome = nome;
        this.valor = valor;
        this.porcentagem = valor/total;
        this.categorias = new ArrayList<>();
    }

    public void addCategoria(ProporcaoVO categoria) {
        categorias.add(categoria);
    }
}
