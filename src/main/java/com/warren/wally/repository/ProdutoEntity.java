package com.warren.wally.repository;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;

@Entity(name = "produto")
public class ProdutoEntity {

	@Id
	@GeneratedValue
	private Long id;
	
	private String corretora;
	private String instituicao;
	
	@Enumerated(EnumType.STRING)
	private TipoInvestimento tipoInvestimento;
	
	@Enumerated(EnumType.STRING)
	private TipoRentabilidade tipoRentabilidade;
	
	private LocalDate dtAplicacao;
	private double valorAplicado;
	private double taxa;
	private LocalDate vencimento;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCorretora() {
		return corretora;
	}
	public void setCorretora(String corretora) {
		this.corretora = corretora;
	}
	public String getInstituicao() {
		return instituicao;
	}
	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}
	public TipoInvestimento getTipoInvestimento() {
		return tipoInvestimento;
	}
	public void setTipoInvestimento(TipoInvestimento tipoInvestimento) {
		this.tipoInvestimento = tipoInvestimento;
	}
	public TipoRentabilidade getTipoRentabilidade() {
		return tipoRentabilidade;
	}
	public void setTipoRentabilidade(TipoRentabilidade tipoRentabilidade) {
		this.tipoRentabilidade = tipoRentabilidade;
	}
	public LocalDate getDtAplicacao() {
		return dtAplicacao;
	}
	public void setDtAplicacao(LocalDate dtAplicacao) {
		this.dtAplicacao = dtAplicacao;
	}
	public double getValorAplicado() {
		return valorAplicado;
	}
	public void setValorAplicado(double valorAplicado) {
		this.valorAplicado = valorAplicado;
	}
	public double getTaxa() {
		return taxa;
	}
	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}
	public LocalDate getVencimento() {
		return vencimento;
	}
	public void setVencimento(LocalDate vencimento) {
		this.vencimento = vencimento;
	}
	
	
	
}


