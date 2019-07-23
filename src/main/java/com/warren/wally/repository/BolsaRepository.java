package com.warren.wally.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface BolsaRepository extends CrudRepository<BolsaEntity, Long> {

	public List<BolsaEntity> findFirstByCodigoOrderByDataDesc(String codigo);
	
	public List<BolsaEntity> findByCodigoAndData(String codigo, LocalDate data);
}
