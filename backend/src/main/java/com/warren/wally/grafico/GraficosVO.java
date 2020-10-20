package com.warren.wally.grafico;

import com.warren.wally.portfolio.ProporcaoVO;
import com.warren.wally.portfolio.VariacaoVO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GraficosVO {

	double patrimonioTotal;
	VariacaoVO variacao;
	GraficoDados proporcao;
	List<ProporcaoVO> proporcoes;
}
