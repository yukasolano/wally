package com.warren.wally.model.dadosmercado;

import lombok.Getter;

@Getter
public enum RendaVariavelInfo {

    ABCB4("AÇÃO", "Banco"),
    BBSE3("AÇÃO", "Seguro"),
    ITSA4("AÇÃO", "Banco"),
    TAEE11("AÇÃO", "Energia"),
    WIZS3("AÇÃO", "Seguro"),
    ALZR11("FII", "Logística"),
    BCRI11("FII", "Papel"),
    GGRC11("FII", "Logística"),
    HGCR11("FII", "Papel"),
    HSML11("FII", "Shopping"),
    KNIP11("FII", "Papel"),
    MALL11("FII", "Shopping"),
    MXRF11("FII", "Papel"),
    RBRR11("FII", "Papel"),
    RNGO11("FII", "Corporativo"),
    VISC11("FII", "Shopping"),
    VRTA11("FII", "Papel"),
    XPLG11("FII", "Logística"),
    XPML11("FII", "Shopping");


    private String tipoInvestimento;
    private String categoria;

    RendaVariavelInfo(String tipoInvestimento,
                      String categoria) {
        this.tipoInvestimento = tipoInvestimento;
        this.categoria = categoria;
    }

    public static RendaVariavelInfo find(String codigo) {
        for (RendaVariavelInfo info : values()) {
            if (info.name().equals(codigo)) return info;
        }
        return null;
    }

}
