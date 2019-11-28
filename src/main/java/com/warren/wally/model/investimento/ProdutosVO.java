package com.warren.wally.model.investimento;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutosVO {
	List<ProdutoVO> produtosRF;
	List<ProdutoFIIVO> produtosRV;

}
