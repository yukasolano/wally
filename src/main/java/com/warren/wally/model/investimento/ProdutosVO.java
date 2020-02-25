package com.warren.wally.model.investimento;

import java.util.List;

import com.warren.wally.repository.MovimentacaoEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutosVO {
	List<ProdutoRFVO> produtosRF;
	List<ProdutoRVVO> produtosRV;
	List<MovimentacaoEntity> extrato;

}
