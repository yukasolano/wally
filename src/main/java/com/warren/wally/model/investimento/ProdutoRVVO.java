package com.warren.wally.model.investimento;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoRVVO {

    public ProdutoRVVO() {
        super();
    }

    public ProdutoRVVO(String codigo) {
        super();
        this.codigo = codigo;
    }

    private String codigo;
    private double precoTotal = 0.0;
    private int quantidade = 0;
    private double cotacao = 0.0;
    private double rentabilidadeDividendo = 0.0;
    private TipoInvestimento tipoInvestimento;
    private double precoMedio;
    private double valorPresente;
    private double resultado;
}
