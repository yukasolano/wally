package com.warren.wally.repository;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.model.investimento.TipoMovimento;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "movimentacao")
@Getter
@Setter
@NoArgsConstructor
public class MovimentacaoEntity {

	@Id
	@GeneratedValue
	private Long id;

	private TipoInvestimento tipoInvestimento;
	private TipoMovimento tipoMovimento;
	private LocalDate data;
	private String codigo;
	private int quantidade;
	private double valorUnitario;
	
	public MovimentacaoEntity(TipoInvestimento tipoInvestimento, TipoMovimento tipoMovimento, LocalDate data,
			String codigo, int quantidade, double valorUnitario) {
		super();
		this.tipoInvestimento = tipoInvestimento;
		this.tipoMovimento = tipoMovimento;
		this.data = data;
		this.codigo = codigo;
		this.quantidade = quantidade;
		this.valorUnitario = valorUnitario;
	}
	
	

}
