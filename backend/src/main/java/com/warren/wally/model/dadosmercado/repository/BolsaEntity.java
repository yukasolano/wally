package com.warren.wally.model.dadosmercado.repository;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "bolsa")
public class BolsaEntity {


	@Id
	@GeneratedValue
	private Long id;
	
	private String codigo;
	
	private LocalDate data;
	
	private double valor;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public LocalDate getData() {
		return data;
	}
	
	public void setData(LocalDate data) {
		this.data = data;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
}