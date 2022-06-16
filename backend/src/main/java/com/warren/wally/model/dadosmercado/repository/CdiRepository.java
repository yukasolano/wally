package com.warren.wally.model.dadosmercado.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CdiRepository extends CrudRepository<CdiEntity, Long> {

	List<CdiEntity> findAllByOrderByDataDesc();

	List<CdiEntity> findAllByOrderByData();

	List<CdiEntity> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

	List<CdiEntity> findByData(LocalDate data);
}
