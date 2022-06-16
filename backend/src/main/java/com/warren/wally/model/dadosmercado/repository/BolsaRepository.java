package com.warren.wally.model.dadosmercado.repository;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface BolsaRepository extends CrudRepository<BolsaEntity, Long> {

	List<BolsaEntity> findFirstByCodigoOrderByDataDesc(String codigo);
	
	List<BolsaEntity> findByCodigoAndData(String codigo, LocalDate data);

	List<BolsaEntity> findByCodigoOrderByDataDesc(String codigo);

	List<BolsaEntity> findByCodigoAndDataLessThanOrderByDataDesc(String codigo,	LocalDate data);
}
