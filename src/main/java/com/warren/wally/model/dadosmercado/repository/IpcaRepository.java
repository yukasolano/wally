package com.warren.wally.model.dadosmercado.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface IpcaRepository extends CrudRepository<IpcaEntity, Long> {

	List<IpcaEntity> findAllByOrderByDataDesc();

	List<IpcaEntity> findFirstByOrderByDataDesc();

	List<IpcaEntity> findAllByOrderByData();

	List<IpcaEntity> findByDataBetween(LocalDate dataInicio, LocalDate dataFim);

	List<IpcaEntity> findByData(LocalDate data);
}