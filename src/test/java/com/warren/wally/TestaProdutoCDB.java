package com.warren.wally;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.warren.wally.model.Calculadora;
import com.warren.wally.model.CalculadoraCDI;
import com.warren.wally.model.CalculadoraPre;
import com.warren.wally.model.ProdutoCDB;

public class TestaProdutoCDB {

	private ProdutoCDB produto;
	private LocalDate hoje;

	
	@Test
	public void testCalculoPre() {
		produto = new ProdutoCDB(LocalDate.of(2017,12,01), LocalDate.of(2022,11,07), 6000, 0.1221);
		produto.setCalc(new CalculadoraPre());
		hoje = LocalDate.of(2019, 01, 18);
		produto.calculaAccrual(hoje);
		assertEquals(6681.10, produto.getValorPresente(), 0.01);
		assertEquals(11.35, produto.getRentabilidadeLiquida()*100, 0.01);
		assertEquals(10.09, produto.getTaxaAnualLiquida()*100, 0.01);
		assertEquals(0.8, produto.getTaxaMensalLiquida()*100, 0.01);
	}
	
	@Test
	public void testCalculoCDI() {
		produto = new ProdutoCDB(LocalDate.of(2016,06,24), LocalDate.of(2021,05,31), 10000, 1.22);
		produto.setCalc(new CalculadoraCDI());
		hoje = LocalDate.of(2018, 12, 10);
		produto.calculaAccrual(hoje);
		assertEquals(12606.04, produto.getValorPresente(), 0.01);
		assertEquals(26.06, produto.getRentabilidadeLiquida()*100, 0.01);
		assertEquals(9.94, produto.getTaxaAnualLiquida()*100, 0.01);
		assertEquals(0.79, produto.getTaxaMensalLiquida()*100, 0.01);
	}
	

}
