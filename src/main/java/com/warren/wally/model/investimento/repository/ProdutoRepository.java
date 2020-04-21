package com.warren.wally.model.investimento.repository;

import com.warren.wally.model.investimento.TipoInvestimento;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface ProdutoRepository extends CrudRepository<ProdutoEntity, Long> {

    List<ProdutoEntity> findByTipoInvestimento(TipoInvestimento tipoInvestimento);

    List<ProdutoEntity> findByCodigo(String codigo);
}
