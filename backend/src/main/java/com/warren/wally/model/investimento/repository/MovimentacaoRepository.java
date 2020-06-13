package com.warren.wally.model.investimento.repository;

import com.warren.wally.model.investimento.TipoMovimento;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface MovimentacaoRepository extends CrudRepository<MovimentacaoEntity, Long> {

    List<MovimentacaoEntity> findByCodigoAndTipoMovimentoAndDataLessThan(String codigo,
                                                                         TipoMovimento tipoMovimento,
                                                                         LocalDate data);

    List<MovimentacaoEntity> findByCodigoAndDataLessThanOrderByData(String codigo,
                                                                    LocalDate data);

    List<MovimentacaoEntity> findByCodigoOrderByData(String codigo);

    List<MovimentacaoEntity> findByTipoMovimentoAndDataLessThan(TipoMovimento tipoMovimento,
                                                                LocalDate data);

    List<MovimentacaoEntity> findByCodigoAndTipoMovimentoAndDataLessThanOrderByDataDesc(String codigo,
                                                                                        TipoMovimento tipoMovimento,
                                                                                        LocalDate data);

    List<MovimentacaoEntity> findByCodigoAndTipoMovimentoAndDataBetweenOrderByDataDesc(String codigo,
                                                                                       TipoMovimento tipoMovimento,
                                                                                       LocalDate dataInicio,
                                                                                       LocalDate dataFim);

    List<MovimentacaoEntity> findByTipoMovimentoAndDataBetweenOrderByData(TipoMovimento tipoMovimento,
                                                                          LocalDate dataInicio,
                                                                          LocalDate dataFim);

    List<MovimentacaoEntity> findByDataBetweenOrderByData(LocalDate dataInicio,
                                                          LocalDate dataFim);

    List<MovimentacaoEntity> findByTipoMovimentoAndData(TipoMovimento tipoMovimento, LocalDate data);

}
