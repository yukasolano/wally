package com.warren.wally.model.investimento.repository;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "produto")
@Getter
@Setter
public class ProdutoEntity {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    private String codigo;

    private String instituicao;

    @Enumerated(EnumType.STRING)
    private TipoInvestimento tipoInvestimento;

    @Enumerated(EnumType.STRING)
    private TipoRentabilidade tipoRentabilidade;

    private double taxa;
    private LocalDate vencimento;


    @Override
    public String toString() {
        return String.format("%s %s %s %.2f", tipoInvestimento, tipoRentabilidade, instituicao, taxa);
    }
}


