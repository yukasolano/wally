package com.warren.wally.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.warren.wally.model.investimento.TipoMovimento;

public interface MovimentacaoRepository extends CrudRepository<MovimentacaoEntity, Long> {

	public List<MovimentacaoEntity> findByCodigoAndTipoMovimentoAndDataLessThan(String codigo, TipoMovimento tipoMovimento, LocalDate data);
	
	public List<MovimentacaoEntity> findByTipoMovimentoAndDataLessThan(TipoMovimento tipoMovimento, LocalDate data);
	
	public List<MovimentacaoEntity> findByCodigoAndTipoMovimentoAndDataLessThanOrderByDataDesc(String codigo, TipoMovimento tipoMovimento, LocalDate data);
	
	public List<MovimentacaoEntity> findByCodigoAndTipoMovimentoAndDataBetweenOrderByDataDesc(String codigo, TipoMovimento tipoMovimento, LocalDate dataInicio, LocalDate dataFim);
	
	public List<MovimentacaoEntity> findByTipoMovimentoAndDataBetweenOrderByData(TipoMovimento tipoMovimento, LocalDate dataInicio, LocalDate dataFim);

	public List<MovimentacaoEntity> findByDataBetweenOrderByData(LocalDate dataInicio, LocalDate dataFim);
	
}
