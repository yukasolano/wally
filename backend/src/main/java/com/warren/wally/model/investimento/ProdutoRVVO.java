package com.warren.wally.model.investimento;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProdutoRVVO implements ProdutoVO {

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
    private double precoMedio = 0.0;
    private Double valorPresente = 0.0;
    private double resultado = 0.0;
    private String instituicao = "";
    private String anoVencimento = "Liquidez diária";

    private List<MovimentacaoEntity> movimentacoes = new ArrayList<>();

    private LocalDate ultimaCompra = null;

    private TipoRentabilidade tipoRentabilidade;

    private LocalDate dataReferencia;

    private List<DividendoVO> dividendos = new ArrayList<>();

    public void addDividendo(DividendoVO dividendoVO) {
        dividendos.add(dividendoVO);
    }

    @Override
    public Double getValorAplicado() {
        return this.precoMedio * this.quantidade;
    }

    public Double getRentabilidade(ProdutoVO vo) {

        if(vo == null) return 1d;
        ProdutoRVVO vo1 = (ProdutoRVVO)vo;
        Double p1 = vo1.getCotacao();
        if(vo1.getDataReferencia().equals(vo1.getUltimaCompra())) {
            p1 = vo1.getPrecoMedio();
        }

        Double p2 = this.getCotacao();
        if(this.getDataReferencia().equals(this.getUltimaCompra())) {
            p2 = this.getPrecoMedio();
        }
        return 1d + Math.log(p2/p1);
    }
}
