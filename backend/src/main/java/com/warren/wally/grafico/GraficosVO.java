package com.warren.wally.grafico;

import com.warren.wally.portfolio.VariacaoVO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GraficosVO {

	double patrimonioTotal;
	VariacaoVO variacao;
	GraficoDados proporcao;
}
