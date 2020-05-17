package com.warren.wally.utils;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
public class DataValor implements Comparable<DataValor>{

	private LocalDate data;
	private double valor;

	public DataValor(String data, String valor) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.data = LocalDate.parse(data, dtf);
		this.valor = Double.parseDouble(valor);
	}

	public DataValor(String data, double valor) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		this.data = LocalDate.parse(data, dtf);
		this.valor = valor;
	}
	
	public DataValor(LocalDate data, double valor) {
		this.data = data;
		this.valor = valor;
	}

	@Override
	public String toString() {
		return "[data=" + data + ", valor=" + valor + "]";
	}

	@Override
	public int compareTo(DataValor o) {
		return this.getData().compareTo(o.getData());
	}

}
