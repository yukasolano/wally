package com.warren.wally.controller;

import com.warren.wally.utils.DataValor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SerieVO {
    private String nome;
    private List<DataValor> valores;

}
