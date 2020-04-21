package com.warren.wally.model.investimento.repository;

import com.warren.wally.model.investimento.TipoMovimento;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "movimentacao")
@Getter
@Setter
@NoArgsConstructor
public class MovimentacaoEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String codigo;

    private String corretora;

    private TipoMovimento tipoMovimento;
    private LocalDate data;
    private int quantidade;
    private double valorUnitario;

    public MovimentacaoEntity(TipoMovimento tipoMovimento,
                              LocalDate data,
                              String codigo,
                              int quantidade,
                              double valorUnitario) {
        super();
        this.tipoMovimento = tipoMovimento;
        this.data = data;
        this.codigo = codigo;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }


}
