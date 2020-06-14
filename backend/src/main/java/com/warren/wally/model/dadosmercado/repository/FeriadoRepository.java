package com.warren.wally.model.dadosmercado.repository;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface FeriadoRepository extends CrudRepository<FeriadoEntity, Long> {

	List<FeriadoEntity> findByData(LocalDate data);

	List<FeriadoEntity> findAll();
}
