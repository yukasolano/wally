package com.warren.wally.model;

public class ProdutoFIIVO {

	public ProdutoFIIVO(String codigo) {
		super();
		this.codigo = codigo;
	}

	private String codigo;
	private double precoTotal = 0.0;
	private int quantidade = 0;
	private double cotacao = 0.0;
	private double rentabilidadeDividendo = 0.0;

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(double precoTotal) {
		this.precoTotal = precoTotal;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public double getRentabilidadeDividendo() {
		return rentabilidadeDividendo;
	}

	public void setRentabilidadeDividendo(double rentabilidadeDividendo) {
		this.rentabilidadeDividendo = rentabilidadeDividendo;
	}

	public double getPrecoMedio() {
		return this.precoTotal / this.quantidade;
	}

	public double getValorPresente() {
		return getQuantidade() * cotacao;
	}

	public double getResultado() {
		return getValorPresente() - getPrecoTotal();
	}

	public void setCotacao(double cotacao) {
		this.cotacao = cotacao;
	}

}
