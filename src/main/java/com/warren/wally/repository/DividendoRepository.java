package com.warren.wally.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DividendoRepository extends CrudRepository<DividendoEntity, Long> {

	public List<DividendoEntity> findByCodigoAndDataLessThan(String codigo, LocalDate data);
	
	public List<DividendoEntity> findByDataLessThan(LocalDate data);
	
	public List<DividendoEntity> findByCodigoAndDataLessThanOrderByDataDesc(String codigo, LocalDate data);
	
	public List<DividendoEntity> findByCodigoAndDataBetweenOrderByDataDesc(String codigo, LocalDate dataInicio, LocalDate dataFim);
	
	public List<DividendoEntity> findByDataBetweenOrderByData(LocalDate dataInicio, LocalDate dataFim);
}
