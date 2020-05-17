package com.warren.wally.model.cadastro;

public interface CadastroProduto<T> {
    void save(T produto);
    void saveGeneric(ProdutoInfoVO vo);
}
