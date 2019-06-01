package com.warren.wally.repository;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "movimentacao")
public class MovimentacaoEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	
	public MovimentacaoEntity(LocalDate data, String codigo, double valorUnitario, int quantidade) {
		super();
		this.data = data;
		this.codigo = codigo;
		this.valorUnitario = valorUnitario;
		this.quantidade = quantidade;
	}
	
	public MovimentacaoEntity() {
		super();
	}
	private LocalDate data;
	private String codigo;
	private double valorUnitario;
	private int quantidade;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate data) {
		this.data = data;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public double getValorUnitario() {
		return valorUnitario;
	}
	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	
}
