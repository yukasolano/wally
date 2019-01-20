package com.warren.wally;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import com.warren.wally.model.Calculadora;
import com.warren.wally.model.CalculadoraPre;
import com.warren.wally.model.ProdutoCDB;

public class TestaProdutoCDB {

	private ProdutoCDB produto;
	private LocalDate hoje;
	@Before
	public void inicializa() {
		produto = new ProdutoCDB(LocalDate.of(2017,12,01), LocalDate.of(2022,11,07), 6000, 12.21);
		produto.setCalc(new CalculadoraPre());
		hoje = LocalDate.of(2019, 01, 18);
		produto.calculaAccrual(hoje);
	}
	
	@Test
	public void testValorPresente() {
		assertEquals(6681.10, produto.getValorPresente(), 0.01);
	}
	
	@Test
	public void testRentabilidadeLiquida() {
		assertEquals(11.35, produto.getRentabilidadeLiquida()*100, 0.01);
	}
	
	@Test
	public void testTaxaAnualLiquida() {
		assertEquals(10.09, produto.getTaxaAnualLiquida()*100, 0.01);
	}
	
	@Test
	public void testTaxaMensalLiquida() {
		assertEquals(0.8, produto.getTaxaMensalLiquida()*100, 0.01);
	}

}
