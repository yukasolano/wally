package com.warren.wally.model.investimento;

import com.warren.wally.model.investimento.repository.MovimentacaoEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProdutosVO {
    List<ProdutoVO> produtos;
    List<MovimentacaoEntity> extrato;

}
