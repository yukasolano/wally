package com.warren.wally.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface MovimentacaoRepository extends CrudRepository<MovimentacaoEntity, Long> {

	public List<MovimentacaoEntity> findByCodigoAndDataLessThan(String codigo, LocalDate data);
	
	public List<MovimentacaoEntity> findByDataLessThan(LocalDate data);
	
}
